package com.zafar.adnetwork.nlp;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zafar.adnetwork.domain.TravelType;
import com.zafar.adnetwork.util.Constants;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;

@Service
public class StanfordNLP {
	private final static Logger logger=LoggerFactory.getLogger(StanfordNLP.class);
	public Set<String> findPlaces(String text){
		TreeMap<String, Integer> wordFrequency=new TreeMap<>();
		String serializedClassifier = "stanford-ner-2015-12-09/classifiers/english.all.3class.distsim.crf.ser.gz";
		try {
			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);		    
		      List<Triple<String, Integer, Integer>> list = classifier.classifyToCharacterOffsets(text);
		      for (Triple<String, Integer, Integer> item : list) {
		    	String place=text.substring(item.second(), item.third());
		    	if(item.first().equals(Constants.PLACE_IDENTIFIER)) { 
		    		
		    		Integer freq=wordFrequency.get(place);
		    		if(freq==null)
		    			wordFrequency.put(place,1);
		    		else
		    			wordFrequency.put(place, freq+1);
		    	}
		    	
		      }
			  logger.debug(wordFrequency.toString());
		
		} catch (ClassCastException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		 return wordFrequency.keySet();
	}
	
	public Set<String> findTravelTypes(String text){
		Set<String> types=new HashSet<String>();
		if(text.toLowerCase().contains("hotel"))
			types.add(TravelType.hotels.name());
		if(text.toLowerCase().contains("flight"))
			types.add(TravelType.flights.name());
		if(text.toLowerCase().contains("holiday") || text.toLowerCase().contains("vacation"))
			types.add(TravelType.holidays.name());
		return types;
		
	}
	//@Test
	public void init(){
		findPlaces("Skip to content Trippathon Traveler Blog Essential Bali travel tips: what to know before you go From navigating the crowds to being monkey-savvy, there are a few tricks to getting the best out of a trip to Bali. We’ve rounded up 15 top tips to bank for your next visit to the Island of the Gods. There are still pockets of serenity to be found beyond the chaos of south Bali. Image by Samantha Chalker / Lonely Planet Expect crowds One of the most touristed islands on earth, Bali isn’t exactly an untouched paradise. But while it can be difficult to escape the throngs ofsouth Bali and Ubud, determined solitude seekers will be pleased to find loads of secluded corners beyond these primary tourist centres. Tip: head to the central mountains, or Bali’s more chilled-out north and west coasts. Choose your base carefully It pays to put some thought into your Bali base, as chaotic traffic and hot weather are likely to make you stick close to your hotel or guesthouse rather than wander far on foot or sit in stuffy taxis. If you’re looking for real R&R, Kuta probably isn’t your thing. If you want to shop up a storm and eat more than your body weight in fine food, a week on Nusa Lembongan isn’t likely to leave you fully satiated. Find your perfect spot with the help of Lonely Planet’s ‘first time Bali’ guide. The fresh, tropical juices on offer in Bali make it easy to stay hydrated. Image by Samantha Chalker / Lonely Planet Don’t fret about ‘Bali belly’ Strict dietary habits are no longer required to prevent spending your Bali break within two steps of a toilet. Once upon a time, salads, cut fruit, ice cubes and most meats were on the danger list, but hygiene standards have improved markedly across the island, and many kitchens offer good quality organic produce. While dodgy prawns will always be out there, by staying hydrated, avoiding notorious local liquor arak and consuming street food with a degree of caution, the dreaded Bali belly should be kept at bay. Dress for the occasion Beachwear doesn’t always cut it in Bali – many higher-end bars, restaurants and clubs enforce a dress code. If you’re unsure, call ahead to save the potential embarrassment of being turned away. Spiritual and religious devotion plays a key role in Balinese life. Image by Samantha Chalker / Lonely Planet Respect religious customs Religion rules the roost in Bali. Don’t get your knickers in a knot when a street is blocked off for a ceremony or your driver pulls over mid-trip to make a blessing – this is all part of the magic of the island. Plan accordingly if your travel dates fall on Nyepi when everything in Bali (even the airport) shuts down for the day, and always dress modestly (covering the shoulders and knees) and conduct yourself appropriately when visiting temples and holy sites. Prepare for a mixed bag of price tags It’s still possible to visit Bali on a shoestring by staying in guesthouses, dining at warungs and shopping at local markets, but you can just as easily blow your life savings as drinks, meals, spa treatments and room rates at high-end establishments are priced similarly to that in Australia, the UK and the US. Look out for online discounts and happy hour deals. Bali’s monkeys are known for their thievery. Image by Samantha Chalker / Lonely Planet Be cautious of wild and stray animals Give wild and stray animals a wide berth. They may look cute, but rabies and other diseases are serious risks in Bali and monkeys are notorious for their thieving ways. Bali’s stray dogs are numerous, and often in pretty bad shape. If you’re keen to make a difference, consider making a ‘doggy donation’ to Bali Dog Refuge (balidogrefuge.com) which helps to rescue and rehabilitate the island’s stray pups. Avoid plastic water bottles Bali’s heat and humidity calls for constant hydration, but consider the environment before purchasing another bottled drink. An estimated three million plastic bottles are discarded in Bali each month; help reduce this figure by investing in a stainless steel bottle that you can refill; most good cafes and restaurants have a water filter available that you can use for free or for a small fee. Earth Café in Seminyak has stainless steel bottles available for purchase. Learn some local lingo A few basic words of Bahasa Indonesia will take you a long way in Bali. Try selamat pagi (good morning), tolong (please) and terima kasih(thank you), for starters. Just another rainy day in Seminyak, Bali. Image by Samantha Chalker / Lonely Planet Remember that low season often means rainy season Be mindful of Bali’s rainy season (January to April and October to November) when planning your trip. Discounts can be great, but if you end up spending your holiday cooped up indoors, you may be left wondering if making the trip was worth it. Fortunately, the rains are often limited to brief afternoon downpours, so your holiday isn’t likely to be a total write-off. Bargain respectfully You can bargain for many items and services in Bali, but do so respectfully and with a smile on your face. You’ll know when the vendor has reached their limit, and at that point don’t push it. When in doubt, walk away – if the seller doesn’t come after you, you can be sure they aren’t prepared to drop their price any lower. Get your head around the current visa situation In early 2015, Indonesia waived its standard 30-day tourist visa-on-arrival (VOA) system for 45 countries; visitors from most other nations (including Australians) must purchase the VOA. While extending a 30-day visa is possible, it can be a tricky business. Speak to a reputable visa agent on the ground, or contact your nearest Indonesian consulate prior to departure. At the time of publication, 60-day visas could be arranged in advance, but not in-country. Follow Bali’s rules, as strange as they may sometimes seem. Image by Samantha Chalker / Lonely Planet Play by the rules The Indonesian legal system may seem confusing and contradictory, but it’s best not argue with police if you are accused of an infringement that may feel unjust, and pay ‘fines’ with good grace. Do not expect any special treatment for being a foreigner, and it goes without saying that having anything to do with drugs is a very bad idea. Respect the ocean Even if you’re an avid beach-goer and surf worshipper, Bali’s powerful waves, strong currents and exposed rocks can be treacherous, so take care, and don’t swim alone unless you are completely confident in doing so. Show equal respect for the beach by not leaving any garbage (including cigarette butts) behind – when the tide comes in, it’ll be sucked into the ocean at great cost to the marine ecosystem. Don’t stress Bali is generally safer than the headlines suggest, but with close to four million tourists hitting its shores every year, it’s statistically natural that some travellers may have problems. Party safe, always wear a helmet when riding a bike or scooter, be respectful, and don’t do anything you wouldn’t do in your home country, and you’re on track for the holiday of a lifetime. Author rootPosted on August 26, 2016August 26, 2016 Leave a Reply Cancel reply Your email address will not be published. Required fields are marked * Comment Name * Email * Website Search for: Search Recent Posts Essential Bali travel tips: what to know before you go Recent Comments Archives August 2016 Categories Uncategorized Meta Log in Entries RSS Comments RSS WordPress.org Trippathon Proudly powered by WordPress");
	}

}
