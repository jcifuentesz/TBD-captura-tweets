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

				System.out.println("user: " + status.getUser().getName());
				System.out.println("tweet: " + status.getText());
                System.out.println("location: " + status.getUser().getLocation());
                System.out.println("--------------------------------------------------------");
                mongo.insert(status);
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
	    filter.track(new String[]{"ciclovía", "ciclovia", "ciclovias", "ciclovias",
                                  "@ciclovías", "@Ciclovías", "#ciclovias", "#ciclovías",
                                  "#Ciclovias", "#Ciclovías", "bicicleta", "Bicicleta", "#Bicicleta",
                                  "#bicicleta", "Bici", "bici"});
	    filter.language(new String[]{"es", "pt"});
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
