### Project 2 : Twitter Analysis

Project 2 is a twitter data analysis project where we use Twitter API to analyze twitter feed from the internet

#### Technologies used
- Scala 2.11.12
- Spark 2.4.7
- SparkSQL
- Spark Streaming
- Twitter API
- HTTPClient
- Scala File Utilities
- Git and GitHub

#### Questions to be answered
- How often is the BTS hashtag used with other hashtags? 
    - What are some of these other hashtags associated with?
- What is the average of likes that a common BTS post gets?
- What are common words that people have used with the BTS hashtag?
    -How has this varied over time?
- How is Kpop being compared/linked to American pop?
    - Which other Kpop artists are famous in the americas?
    - Which artist is the most trending on Twitter?
    - Which states/country tweets the most about Kpop?
- Analysis on tweets with BTS/BTS-related hashtags and mentions in different languages
    - How many languages are tweeting about BTS?
    - What is the distribution of languages tweeting about BTS? 
- How is the #BTS distributed over different geo-locations?

#### TO-DO
1. Learn/Understand Twitter API
2. Learn/Understand Spark/Scala 
    (and other useful technologies and frameworks related to our project)
3. Create queries related to our questions
4. Execute queries and analyze
5. Create Google slides for the presentation 
6. Practice Presentation 


#### Data Gathering

We used the curl command line below to gather filtered stream using twitter API.

``` 
curl 'https://api.twitter.com/2/tweets/search/stream?tweet.fields=lang,geo,public_metrics,created_at&expansions=geo.place_id&place.fields=full_name' -H "Authorization: Bearer $TWITTER_BEARER_TOKEN"
```

In the twitter API we added rules that will allow twitter's filtered stream to return tweets that contains `#kpop`, `@kpop`, `#k_pop`, `@k_pop`, `#BTS`, `@BTS` tags.


Using the curl command line below we added these rules to the twitter API
```
curl -X POST 'https://api.twitter.com/2/tweets/search/stream/rules' \
-H "Content-type: application/json" \
-H "Authorization: Bearer $TWITTER_BEARER_TOKEN" -d \
'{
  "add": [
    { 
        "value": "@bts OR #bts",
        "tag": ""
    },
    {
        "value": "@kpop OR #kpop OR @k_pop OR #k_pop",
        "tag": ""
    }
  ]
}'
```

#### Jar Files
The jar files that are used in this project are contained in the `jar files` folder.


### Google Slide Presentation

[group talk](https://docs.google.com/presentation/d/1MCluH6Jtp52zT6qFNj7vkehwvd70PPPzB8jWmgj6Ed4/edit?usp=sharing)
