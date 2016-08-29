package com.zafar.adnetwork.dao;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import rx.Observable;

import com.zafar.adnetwork.domain.Response;
import com.zafar.adnetwork.executors.ExecutorUtilImpl;
import com.zafar.adnetwork.model.AdSelectorRule;
import com.zafar.adnetwork.model.Advertisement;
import com.zafar.adnetwork.model.City;
import com.zafar.adnetwork.model.CrawledInfo;
import com.zafar.adnetwork.model.Dashboard;
import com.zafar.adnetwork.model.PartnerInfo;
import com.zafar.adnetwork.model.PartnerUrl;
import com.zafar.adnetwork.nlp.StanfordNLP;
import com.zafar.adnetwork.util.Constants;
import com.zafar.adnetwork.util.Utilities;

@Service
public class AdvertisementService {

	private EntityManager manager;
	
	@Value("${crawl.time.period.s:300}")
	private int defaultCrawlPeriod;
	
	@Value("${default.ad.id:2}")
	private int defaultAdId;
	
	@Autowired
	private StanfordNLP stanfordNLP;
	
	@Autowired
	private ExecutorUtilImpl executors;
	
	@Autowired
	private Utilities util;
	
	private final static Logger logger=LoggerFactory.getLogger(AdvertisementService.class);

	
	@PostConstruct
	public void init(){
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("adnetwork");
		manager = emfactory.createEntityManager();
	}
	public DeferredResult<String> getAd(String url) {
		DeferredResult<String> ads=util.emptyStringResponseWithTimeout();
		Observable.just(url).subscribeOn(executors.getReadExecutors()).subscribe(
				(pageUrl)->{
					PartnerUrl partner=manager.find(PartnerUrl.class, url);
					CrawledInfo crawled=manager.find(CrawledInfo.class, partner.getPartnerUrl());
					Query q=manager.createNativeQuery("select * from ad_selector_rules where city_id in ("+crawled.getCityNames()+") "
							+ "and travel_type in ("+travelTypeToId(crawled.getTravelTypeIds())+") and other in ("+crawled.getOtherKeywords()+")", AdSelectorRule.class);
					try{
						List<AdSelectorRule> rules=q.getResultList();
						String response="";
						if(rules!=null && rules.size()==0){
							//set the default rule
							Advertisement ad=manager.find(Advertisement.class, partner.getDefaultAdId());
							//response="<a href='"+ ad.getDestinationUrl() +"'><img style='width:100%;height:100%;' src='"+ ad.getAdImageUrl() +"'/></a>";
							response="<a href='#' onclick=\"analytics();window.open('"+ad.getDestinationUrl()+"');return false;\"><img style='width:100%;height:100%;' src='"+ad.getAdImageUrl()+"'/></a>";
						}
						
						else{
							Advertisement ad=manager.find(Advertisement.class, rules.get(0).getAdId());
					//		response="<a href='"+ ad.getDestinationUrl() +"'><img style='width:100%;height:100%;' src='"+ ad.getAdImageUrl() +"'/></a>";
							response="<a href='#' onclick=\"analytics();window.open('"+ad.getDestinationUrl()+"');return false;\"><img style='width:100%;height:100%;' src='"+ad.getAdImageUrl()+"'/></a>";	
						}
						Dashboard d=manager.find(Dashboard.class,pageUrl);
						if(d!=null){
							manager.getTransaction().begin();
							d.setImpressions(d.getImpressions()+1);
							manager.persist(d);
							manager.getTransaction().commit();
						}else{
							d=new Dashboard();
							d.setPartnerUrl(pageUrl);
							d.setImpressions(1);
							d.setClicks(0);
							manager.getTransaction().begin();
							manager.persist(d);
							manager.getTransaction().commit();
						}
						ads.setResult(response);
					}catch(Exception e){
						logger.error("some problem",e);
					}
				},
				(exception)->{
			}
		);
		return ads;
	}
	public void register(String url[], String partnerName) {
		DeferredResult<Response<CrawledInfo>> ads=util.emptyCrawlResultWithTimeout();
		int partnerId=0;
		PartnerInfo p=new PartnerInfo();
		p.setPartnerName(partnerName);
		manager.getTransaction().begin();
		manager.persist(p);	
		manager.getTransaction().commit();
		partnerId=p.getPartnerId();
		
		for(String u:url){
			PartnerUrl pu=manager.find(PartnerUrl.class, u);
			if(pu==null){
				pu=new PartnerUrl();
				pu.setDefaultAdId(defaultAdId);
				pu.setPartnerId(partnerId);
				pu.setPartnerUrl(u);
				manager.getTransaction().begin();
				manager.persist(pu);
				manager.getTransaction().commit();

				subscribeCrawl(u);
			}			
		}
		
		
	}
	public void subscribeCrawl(String u){
		Observable.just(u).delay(defaultCrawlPeriod, TimeUnit.SECONDS,executors.getWriteExecutors()).subscribe(
				(urlToCrawl)->{
					crawl(u);
		//			subscribeCrawl(u);			
				},
				(exception)->{
					
				}
			);
	}
	public void crawl(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
	        String text = doc.body().text();
	        Set<String> travelTypes=stanfordNLP.findTravelTypes(text);
	        Set<String>  places=stanfordNLP.findPlaces(text);
	        String metaTags= extractFromMetaTags(doc);
	        //store in DB
	        store(url,travelTypes, places, metaTags);
	        
		} catch (Exception e) {
			logger.error("Oops",e);
		}
			
	}
	private void store(String url, Set<String> travelTypes, Set<String> places,
			String metaTags) {
		CrawledInfo crawled=new CrawledInfo();
		crawled.setCityNames(concat(mapToId(places)));
		crawled.setOtherKeywords(metaTags);
		crawled.setPageId(url);
		crawled.setTravelTypeIds(concatenate(travelTypes));
		crawled.setCrawlPeriod(new Time(defaultCrawlPeriod*1000));
		manager.getTransaction().begin();
		manager.persist(crawled);
		manager.getTransaction().commit();
	}
	private Set<Integer> mapToId(Set<String> places) {
		StringBuffer buffer=new StringBuffer();
		for(String s:places)
			buffer.append("'").append(s).append("',");
		Query q=manager.createNativeQuery("select * from city where city_name in ("+buffer.substring(0, buffer.length()-1)
		+")", City.class);		
		List<City> city=q.getResultList();
		Set<Integer> set=new HashSet<>();
		for(City c:city){
			set.add(c.getCityId());
		}
		return set;
	}
	
	private String travelTypeToId(String travelTypes) {
		StringBuffer buffer=new StringBuffer();
		
		String[] travelTypArr = travelTypes.split(",");
		
		for(String s:travelTypArr)
			buffer.append("'").append(s).append("',");
		Query q=manager.createNativeQuery("select * from travel_types where travel_type_name in ("+buffer.substring(0, buffer.length()-1)
		+")", com.zafar.adnetwork.model.TravelType.class);		
		List<com.zafar.adnetwork.model.TravelType> tt=q.getResultList();
		buffer  = new StringBuffer();
		for(com.zafar.adnetwork.model.TravelType t:tt){
			buffer.append(t.getId()).append(",");
		}
		return buffer.substring(0,buffer.length() - 1);
	}
	
	private String concat(Set<Integer> strings){
		StringBuffer buf=new StringBuffer();
		for(Integer s:strings){
			buf.append(s).append(",");
		}
		return buf.substring(0, buf.length()-1);
	}
	private String concatenate(Set<String> strings){
		StringBuffer buf=new StringBuffer();
		for(String s:strings){
			buf.append(s).append(Constants.CITY_DELIMITER);
		}
		return buf.substring(0, buf.length()-1);
	}
	private String extractFromMetaTags(Document doc) {
		Elements elements=doc.getElementsByTag("meta");
		for(Element e:elements){
			if(e.attr("name").equals("description")){
				return e.attr("content");
			}
		}
		return "";
	}
	//@Test
	public void crawlTest(){
		crawl("http://172.16.43.137/wordpress/index.php/2016/08/26/essential-bali-travel-tips-what-to-know-before-you-go/");;
	}
	public void test(){
		try {
			Document doc = Jsoup.connect("http://172.16.43.137/wordpress/2016/08/26/essential-bali-travel-tips-what-to-know-before-you-go/").get();
	        String text = doc.body().text();
	        System.out.println(extractFromMetaTags(doc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Dashboard getMetrics(String url) {
		Dashboard d=manager.find(Dashboard.class, url);
		return d;
	}
	public void increaseClick(String url) {
		Dashboard d=manager.find(Dashboard.class,url);
		if(d!=null){
			manager.getTransaction().begin();
			d.setClicks(d.getClicks()+1);
			manager.persist(d);
			manager.getTransaction().commit();
		}else{
			d=new Dashboard();
			d.setPartnerUrl(url);
			d.setImpressions(1);
			d.setClicks(1);
			manager.getTransaction().begin();
			manager.persist(d);
			manager.getTransaction().commit();
		}

	}
}
