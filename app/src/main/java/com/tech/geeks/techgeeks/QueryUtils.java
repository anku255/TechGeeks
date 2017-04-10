package com.tech.geeks.techgeeks;


import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * A helper class containing methods to fetch News objects from The Guardian server and parse it.
 */

public class QueryUtils {

    // LOG_TAG for log messages
    private static final String LOG_TAG = "QueryUtils";

    // Sample JSONResponse String that we get from Guardian website
    private static final String SAMPLE_JSON_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":27647,\"startIndex\":1,\"pageSize\":5,\"currentPage\":1,\"pages\":5530,\"orderBy\":\"newest\",\"results\":[{\"id\":\"technology/2017/apr/09/20-apps-get-you-out-about-stuart-dredge\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-09T05:59:00Z\",\"webTitle\":\"20 apps to get you out and about\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/09/20-apps-get-you-out-about-stuart-dredge\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/09/20-apps-get-you-out-about-stuart-dredge\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/75883ab3dd88c05e6c10d6463063b6f0bdb18adb/0_265_5568_3341/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/09/volvo-v90-estate-car-review-martin-love\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-09T05:00:22Z\",\"webTitle\":\"Volvo V90: car review | Martin Love\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/09/volvo-v90-estate-car-review-martin-love\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/09/volvo-v90-estate-car-review-martin-love\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/a109810d203d9911f8654875319355bf2aa655de/128_232_4330_2598/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/09/virtual-reality-is-it-the-future-of-television\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-08T23:04:14Z\",\"webTitle\":\"Virtual reality: Is this really how we will all watch TV in years to come?\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/09/virtual-reality-is-it-the-future-of-television\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/09/virtual-reality-is-it-the-future-of-television\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/9a8e44fe55246cd7ceba2656856e14fd72374bc9/0_223_4300_2581/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/08/speed-reading-apps-can-you-really-read-novel-in-your-lunch-hour\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-08T17:00:07Z\",\"webTitle\":\"Speed-reading apps: can you really read a novel in your lunch hour?\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/08/speed-reading-apps-can-you-really-read-novel-in-your-lunch-hour\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/08/speed-reading-apps-can-you-really-read-novel-in-your-lunch-hour\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/10d1e6007151626ad46b5b2de06fc8aadbce87d3/0_150_4592_2757/500.jpg\"},\"isHosted\":false},{\"id\":\"technology/2017/apr/08/mazda-3-car-review-zoe-williams\",\"type\":\"article\",\"sectionId\":\"technology\",\"sectionName\":\"Technology\",\"webPublicationDate\":\"2017-04-08T10:00:01Z\",\"webTitle\":\"Mazda 3 2.0 car review – ‘It’s an actively fun drive’\",\"webUrl\":\"https://www.theguardian.com/technology/2017/apr/08/mazda-3-car-review-zoe-williams\",\"apiUrl\":\"https://content.guardianapis.com/technology/2017/apr/08/mazda-3-car-review-zoe-williams\",\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/0de3f420be9cd73dcd4b454a2367ad9ef7de95a1/123_510_4405_2644/500.jpg\"},\"isHosted\":false}]}}";

    /**
     * A private constructor because we don't need to create an instance of this class
     * It contains static methods which can be called with class name
     */
    private QueryUtils() {

    }

    /**
     * Return a list of News Objects that it gets from parsing the JSONResponse
     */

    public static ArrayList<News> extractNews() {

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

                // Get the JSONObject for key "fields"
                JSONObject fields = currentNews.getJSONObject("fields");

                // Extract the value for key called "thumbnail"
                String thumbnailUrl = fields.getString("thumbnail");

                // Create a new News Object and add it to ArrayList
                News news = new News(title, newsUrl);
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
