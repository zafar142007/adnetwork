###A content-driven advertisement network for travel websites
This product helps you to distribute advertisements to partner sites (like travel blogs) in a seamless fashion. Partners register with you for showing your advertisements and you pay them on a Cost-Per-Click basis. They provide you with a list of URLs and you give them an html snippet to put on their pages. The product gives you elementary dashboards for viewing impressions and clicks of a page URL to calculate payout amount. 


The partner URLs thus registered with us will be crawled at regular intervals to find out the context of the pages such as city name<sup>[1]</sup>. When that page is loaded on the browser, the relevant advertisements are chosen in realtime and shown on the page.

Deployment steps:

1. Execute the file db.sql on a MySQL database server with hostname 'database.mysql'. Change the database connection credentials in persistence.xml.

2. Configure rules of ad selection in the table 'ad_selector_rules' and create ads in the table 'advertisement'.
 
3. Build the project using maven and deploy on a web container like Tomcat on a server with hostname 'adnetwork.api'. Register partner URLs with the application using the rest endpoint {HOST}/adnetwork/register. 

4. On the partner site, insert the html listed in the file 'partner-integration-snippet.html'.

5. You can see the number of impressions and clicks for a URL u on the endpoint {HOST}/adnetwork/dashboard?pageUrl={u}

_[1] Jenny Rose Finkel, Trond Grenager, and Christopher Manning. 2005. Incorporating Non-local Information into Information Extraction Systems by Gibbs Sampling. Proceedings of the 43nd Annual Meeting of the Association for Computational Linguistics (ACL 2005), pp. 363-370._
