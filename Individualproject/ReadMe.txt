My Individual Project is on File Indexing services.

Minimum Requirements:
1.) Internet connection.
2.) jsoup-1.10.2.jar    file added to externaljar folder.
	can be downloaded at https://jsoup.org/download


Steps for running the project:
1.) Unzip the files.
2.) Go to https://jsoup.org/download and download jsoup-1.10.2.jar core library.
3.) Copy the downloaded 'jsoup-1.10.2.jar' file to 'externaljar' folder of the individualproject folder.
4.) Open a terminal and do 'cd Individualproject' and then run 'ant'.
5.) After running the code open the Individualproject folder and you will find the indexed HTML files.	

	
Implemented Executor framework and used Concurrent Collections as much as possible.
What does it do? 
1.) FileCrawler crawls through the entire tree structure starting from the root.
2.) FileIndexes Indexes directories, links and files.
3.) FileCache caches the indexed content (If required performs FIFO replacement algorithm). 
4.) WebCrawler crawls a given url(in my code www.google.com) on the internet and 
	indexes(downloads) the HTML files to the local homework directory.



For Web Crawler implementation, 
1.) First, I tried to parse the webpage using 'HTML Parser'.
2.) After unsuccessful trials I ended up using 'Jsoup' java library for HTML parsing. 


I have added the jsoup-1.10.2.jar in "externaljar" folder. 

Thank you.