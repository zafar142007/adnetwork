###A content-driven advertisement network for travel websites
This product helps you to distribute advertisements to partner sites (like travel blogs) in a seamless fashion. Partners register with you for showing your advertisements and you pay them on a Cost-Per-Click basis. They provide you with a list of URLs and you give them an html snippet to put on their pages. The product gives you elementary dashboards for viewing impressions and clicks of a page URL to calculate payout amount. 


The partner URLs thus registered with us will be crawled at regular intervals to find out the context of the pages such as city names[1]. When that page is loaded on the browser, the relevant advertisements are chosen in realtime and shown on the page.

Deployment steps:
1. Execute the file db.sql on a MySQL database server with hostname 'database.mysql'. 
2. Build the project using maven and deploy on a web container like Tomcat on a server with hostname 'adnetwork.api'.
3. On the partner site, insert the html listed in the file 'partner-integration-snippet.html'.

_[1] Jenny Rose Finkel, Trond Grenager, and Christopher Manning. 2005. Incorporating Non-local Information into Information Extraction Systems by Gibbs Sampling. Proceedings of the 43nd Annual Meeting of the Association for Computational Linguistics (ACL 2005), pp. 363-370._
