package com.tbd.twitter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import twitter4j.FilterQuery;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.json.DataObjectFactory;

@Service
@Configurable
public class TwitterListener {
	@Autowired
	private TwitterStream twitterStream;
	@Autowired
	private MongoTemplate mongo;
		
	@PostConstruct
	public void run() {
		twitterStream.addListener(new StatusListener() {
			public void onStatus(Status status) {
			    // mostrar en terminal los tweets
				if(status.getUser().getLocation().contains("Chile") || status.getUser().getLocation().contains("chile") ||
						status.getPlace().getCountry().contains("Chile") || status.getPlace().getCountry().contains("chile")) {
					System.out.println("user: " + status.getUser().getName());
					System.out.println("tweet: " + status.getText());
					System.out.println("location: " + status.getUser().getLocation());
					//System.out.println("place: " + status.getPlace().getCountry());
					System.out.println("--------------------------------------------------------");
					mongo.insert(status);
				}
	        }

			@Override
			public void onException(Exception arg0) {

			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
							
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
								
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
								
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
								
			}			
		});
		
	    FilterQuery filter=new FilterQuery();
	    //	bolsa de prueba 1
	    String[] bolsa1 = new String[]{"LGBTI","#gaychile","#bisexual","gay", "gays", "Gay", "Gays",
				"lesbiana", "lesbianas", "Lesbiana", "Lesbianas", "homosexual", "homosexuales", "Homosexual", "Homosexuales",
				"minoría sexual", "ley identidad de género", "identidad de género", "Identidad de género", "Identidad de Género",
				"transgenero", "Transgenero", "maraco", "marako", "fleto", "Fleto", "fletos", "Fletos",
				"Maraco", "Marako", "LGTB", "maricon", "Marikon", "Maricon", "marikon", "maricones", "Marikones", "Maricones",
				"marikones", "lela", "lelas", "Lela", "Lelas", "camionas", "camiona", "Camionas", "Camiona", "#LGTB", "#SoyGay",
				"#SerGayNoEsUnDelito", "#SoyHomosexual", "@Movilh", "#LGTBFracasoDeSociedad", "#LeyDeIdentidadDeGéneroAhora"};

	    //	bolsa de prueba 2
		String[] bolsa2 = new String[]{"#gaychile","#LGBT","#LGTB", "#SoyGay", "#SerGayNoEsUnDelito", "#SoyHomosexual", "@Movilh", "#LGTBFracasoDeSociedad"};

		String[] bolsa3 = new String[]{"danielaVega", "mujerFantastica", "comunidadGay", "gay",
				"homoparental", "#gays",
				"movilh", "LGTBFracasoDeSociedad", "IdeologiaDeGenero",
				"unaMujerFantastica", "matrimonioIgualitario","Movilh"};

		filter.track(bolsa1);
	    filter.language(new String[]{"es"});
	    //filter.locations(new double[]{-71.0000000,-30.0000000}) ubicación;
	    twitterStream.filter(filter);
	}

	public TwitterStream getTwitterStream() {
		return twitterStream;
	}

	public void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public MongoTemplate getMongo() {
		return mongo;
	}

	public void setMongo(MongoTemplate mongo) {
		this.mongo = mongo;
	}
}
