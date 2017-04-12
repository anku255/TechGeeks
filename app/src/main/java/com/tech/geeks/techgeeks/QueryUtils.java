package com.tech.geeks.techgeeks;


import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


/**
 * A helper class containing methods to fetch News objects from The Guardian server and parse it.
 */

public class QueryUtils {

    // Context
    private Context context;

    // LOG_TAG for log messages
    private static final String LOG_TAG = "QueryUtils";

    // Sample JSONResponse String that we get from Guardian website
    private static final String SAMPLE_JSON_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":27657,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":2766,\"orderBy\":\"newest\",\"results\":[{\"id\":\"technology/2017/apr/12/delivery-robots-doordash-yelp-sidewalk-problems\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-12T10:00:32Z\",\"webTitle\":\"Delivery robots: a revolutionary step or sidewalk-clogging nightmare?\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/12/delivery-robots-doordash-yelp-sidewalk-problems\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/12/delivery-robots-doordash-yelp-sidewalk-problems\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/ff76ce10a3b0ec7f4f4b91f6afb3c5ee39882ddd/0_95_5760_3456/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/12/ubers-head-of-communications-quits-scandal-hit-cab-app\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-12T08:49:11Z\",\"webTitle\":\"Uber's head of communications quits scandal-hit cab app\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/12/ubers-head-of-communications-quits-scandal-hit-cab-app\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/12/ubers-head-of-communications-quits-scandal-hit-cab-app\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/ec47ea2733dfd84afe98ae465f5fe52f856515c9/0_1157_2886_1732/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/11/ghosts-gardens-and-poetry-the-festival-exploring-the-far-edges-of-game-design\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-11T11:10:36Z\",\"webTitle\":\"13 games that will change the way you think about gaming\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/11/ghosts-gardens-and-poetry-the-festival-exploring-the-far-edges-of-game-design\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/11/ghosts-gardens-and-poetry-the-festival-exploring-the-far-edges-of-game-design\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/0b9ec10d62e0bc06a07417233ca0c7d303092cd0/438_141_674_405/500.png\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/11/meet-the-millennials-making-big-money-riding-chinas-bitcoin-wave\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-11T06:00:21Z\",\"webTitle\":\"Meet the millennials making big money riding China's bitcoin wave\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/11/meet-the-millennials-making-big-money-riding-chinas-bitcoin-wave\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/11/meet-the-millennials-making-big-money-riding-chinas-bitcoin-wave\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/2d9e88aaec62a1254c67f973af062c07d9b70bcf/0_537_4919_2952/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/11/tilted-device-could-pinpoint-pin-number-for-hackers-study-claims\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-11T05:01:19Z\",\"webTitle\":\"Tilted device could pinpoint pin number for hackers, study claims\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/11/tilted-device-could-pinpoint-pin-number-for-hackers-study-claims\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/11/tilted-device-could-pinpoint-pin-number-for-hackers-study-claims\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/2e31314a8f8a7bb0af50ed8d4a673b502a215033/0_102_3220_1932/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/10/we-need-a-plan-for-tech-not-a-wishlist\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-10T18:04:46Z\",\"webTitle\":\"We need a plan for tech, not a wishlist | Letters\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/10/we-need-a-plan-for-tech-not-a-wishlist\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/10/we-need-a-plan-for-tech-not-a-wishlist\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/2b06771478de0218bb4407cbdbb0b5d7354aaf08/0_51_3500_2100/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/10/tesla-most-valuable-car-company-gm-stock-price\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-10T16:37:58Z\",\"webTitle\":\"Tesla surpasses GM to become most valuable car company in US\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/10/tesla-most-valuable-car-company-gm-stock-price\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/10/tesla-most-valuable-car-company-gm-stock-price\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/59c67f07c97f1555f05d00e075a1a2c42664b120/0_176_3000_1800/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/10/deepminds-alphago-to-take-on-five-human-players-at-once\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-10T14:53:32Z\",\"webTitle\":\"DeepMind's AlphaGo to play on team with humans and to challenge five at once\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/10/deepminds-alphago-to-take-on-five-human-players-at-once\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/10/deepminds-alphago-to-take-on-five-human-players-at-once\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/1264e7dc4a8e954fe28f46b1bc5590f6edeaf57b/0_0_4492_2695/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/10/five-video-games-that-teach-you-how-to-be-better-at-video-games\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-10T12:33:38Z\",\"webTitle\":\"Five video games that teach you how to be better at video games\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/10/five-video-games-that-teach-you-how-to-be-better-at-video-games\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/10/five-video-games-that-teach-you-how-to-be-better-at-video-games\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/702ee3f9ed3d7582d6f5a83dd283b0d52a169538/121_0_1799_1080/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/10/amazon-fire-tv-stick-review-cheap-great-tv-streaming-device-with-new-interface-and-alexa\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-10T10:31:17Z\",\"webTitle\":\"Amazon Fire TV Stick review: cheap, great TV streaming device with new interface and Alexa\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/10/amazon-fire-tv-stick-review-cheap-great-tv-streaming-device-with-new-interface-and-alexa\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/10/amazon-fire-tv-stick-review-cheap-great-tv-streaming-device-with-new-interface-and-alexa\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/55ca0e84ae96462ceec0157999508f1d9db50dc4/1_0_3589_2154/500.jpg\"},\"isHosted\":false}]}}";
    /**
     * Constructor for class QueryUtils
     * We are initializing the context here
     */
    public QueryUtils(Context context) {
        this.context = context;
    }

    /**
     * Return a list of News Objects that it gets from parsing the JSONResponse
     */

    public ArrayList<News> extractNews() {

        // Create an empty ArrayList to which we can add News objects
        ArrayList<News> newsList = new ArrayList<>();

        // Try to parse the SAMPLE_JOSN_RESPONSE. If an exception occurs then
        // print the error message to log

        try {
            // Create a JSONObject from the SAMPLE_JSON_RESPONSE
            JSONObject baseJsonResonse = new JSONObject(SAMPLE_JSON_RESPONSE);

            // Extract the JSONObject associated with the key "response"
            JSONObject response = baseJsonResonse.getJSONObject("response");

            // Extract the JSONArray associated with the key "results"
            JSONArray newsArray = response.getJSONArray("results");

            // For each news in newsArray create a News object
            for(int i=0; i < newsArray.length(); i++) {

                // Get the currentNews JSONObject
                JSONObject currentNews = newsArray.getJSONObject(i);

                // Extract the value for key called "webTitle"
                String title = currentNews.getString("webTitle");

                // Extract the value for key called "webUrl"
                String newsUrl = currentNews.getString("webUrl");

                // Extract he value for "webPublicationDate"
                String pulishDate = currentNews.getString("webPublicationDate");

                // Get the JSONObject for key "fields"
                JSONObject fields = currentNews.getJSONObject("fields");

                // Extract the value for key called "thumbnail"
                String thumbnailUrl = fields.getString("thumbnail");

                // Create a new News Object and add it to ArrayList
                News news = new News(title,thumbnailUrl,newsUrl,pulishDate);
                newsList.add(news);

            }


        } catch (JSONException e) {

            // If an exception is thrown for executing any of the above line then
            // print the error message to log

            Log.e(LOG_TAG,"Problem parsing the JSONResponse",e);
        }

        // Return the list of News Objects
        return newsList;
    }






}
