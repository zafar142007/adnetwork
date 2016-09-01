package com.zafar.adnetwork;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.zafar.adnetwork.dao.AdvertisementService;
import com.zafar.adnetwork.domain.RegisterRequest;
import com.zafar.adnetwork.model.Dashboard;
import com.zafar.adnetwork.util.Constants;

@Controller
public class MainController {
	
	private final static Logger logger=LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private AdvertisementService service;
	
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(ModelMap model, @RequestBody RegisterRequest payload) {
		service.register(payload.getUrlPaths(), payload.getPartnerName());
	}
	@ResponseBody
	@RequestMapping(value = "/getAd", method = RequestMethod.GET)
	public DeferredResult<String> getAd(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("pageUrl");
		response.addHeader("Access-Control-Allow-Origin", "*");
		return service.getAd(url);
	}
	@RequestMapping(value = "/crawlUrl", method = RequestMethod.GET)
	public String crawl(ModelMap model, HttpServletRequest request) {
		String url = request.getParameter("pageUrl");
		service.crawl(url);
		return Constants.DONE;
	}
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String removeUrl(ModelMap model, HttpServletRequest request) {
		String url = request.getParameter("pageUrl");
		service.deregister(url);
		return Constants.DONE;
	}
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dash(ModelMap model, HttpServletRequest request) {
		String url = request.getParameter("pageUrl");
		Dashboard d=service.getMetrics(url);
		if(d!=null){
		model.addAttribute(Constants.CLICKS, d.getClicks());
		model.addAttribute(Constants.IMPRESSIONS, d.getImpressions());
		model.addAttribute(Constants.URL, url);
		}else{
			model.addAttribute(Constants.CLICKS, 0);
			model.addAttribute(Constants.IMPRESSIONS, 0);
			model.addAttribute(Constants.URL, url);
		}
		return Constants.DASH;
	}
	@RequestMapping(value = "/click", method = RequestMethod.GET)
	public String click(ModelMap model, HttpServletRequest request) {
		String url = request.getParameter("pageUrl");
		service.increaseClick(url);
		return Constants.DONE;
	}
	@RequestMapping(value = "/ad.htm", method = RequestMethod.GET)
    @ResponseBody
    public String getAd() {	 		 
	 String mezelHost = "adnetwork.api";
	 String adDivId = "anAd";
	 return "var client_id = \"\";var pageUrl = window.location.href;var width=document.getElementById('"+adDivId+"').style.width;varheight=document.getElementById('"+adDivId+"').style.height;var clientId = document.getElementById(\""+adDivId+"\").innerHTML;var url = 'http://" + mezelHost +"/adnetwork/getAd?pageUrl='+encodeURIComponent(pageUrl)+'&clientId='+clientId+'&width='+width+'&height='+height;varrequest = new XMLHttpRequest();request.onreadystatechange = function() {if (request.readyState === 4) {if (request.status === 200) {document.body.className = 'ok';var anAd = request.responseText;console.log(anAd);document.getElementById(\""+adDivId+"\").innerHTML= anAd;} else {document.body.className = 'error';}}};request.open(\"GET\", url , true);request.send(null);function analytics() {var pageUrl = window.location.href;var url = 'http://" + mezelHost + "/adnetwork/click?pageUrl='+encodeURIComponent(pageUrl);var request = new XMLHttpRequest();request.onreadystatechange = function() {if (request.readyState === 4) {if (request.status === 200) {document.body.className = 'ok';var anAd = request.responseText;console.log(anAd);} else {document.body.className = 'error';}}};request.open(\"GET\", url , true);request.send(null);}";

	 }
	
}
