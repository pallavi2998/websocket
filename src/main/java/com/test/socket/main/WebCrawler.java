package com.test.socket.main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;


public class WebCrawler {

    int max_depth=1;
    private HashSet<String> urlLink;
   // private HashSet<String> actors;

    private HashMap<String, List<String>> cast;

    public WebCrawler() {
        urlLink = new HashSet<String>();
        cast = new HashMap<>();
    }

    public void getMovies(String url,int n,int m){


            try{

                Document document =  Jsoup.connect(url).get();
               //  Elements listItems = document.getElementsByClass("ipc-metadata-list-summary-item sc-3f724978-0 enKyEL cli-parent");
               // Elements listItems = document.select("li.ipc-metadata-list-summary-item sc-3f724978-0 enKyEL cli-parent");
                Elements listItems = document.getElementsByClass("ipc-title-link-wrapper");

                System.out.println("Movies :"+ listItems.size());
                int ct=1;
                for(Element li : listItems){
                    if(ct>n)
                        break;
                    //li.select("a.ipc-title-link_wrapper").first();

                    String movieUrl = li.attr("abs:href");
                    String movieName=  li.getElementsByTag("h3").first().text();
                    urlLink.add(movieUrl);
                    System.out.println("Movies :"+ movieName);
                    List<String> actors = getActors(movieUrl,m);
                    cast.put(movieName,actors);
                    ct++;

                }

                for (HashMap.Entry<String,List<String>> mapElement : cast.entrySet()) {
                    String key = mapElement.getKey();

                    System.out.println("MOVIE" + " : " + key);
                    System.out.println("CAST" + " : ");
                    mapElement.getValue().forEach(x->{
                               System.out.println(x);
                            }
                    );
                }
            }catch (Exception e){
                System.out.println("Exception in getMovies:" +e);
                e.printStackTrace();
            }

    }
    public List<String> getActors(String url,int m){
        List<String> actors= new ArrayList<>();

            try{
                Document doc =  Jsoup.connect(url).get();
                Elements links = doc.getElementsByAttributeValue("data-testid","title-cast-item__actor");
             // System.out.println("Actors: "+links.size());
              int ct=0;
              for(Element l : links ){
                  if(ct>m)
                      break;
                actors.add( l.text());
                ct++;
              }

            }catch (Exception e){
                System.out.println("Exception in getActors:"+e);
                e.printStackTrace();
            }

        return actors;
    }
//https://www.imdb.com/chart/top/
    public static void main(String[] args) {
        WebCrawler wc = new WebCrawler();

        // pick a URL from the frontier and call the getPageLinks()method
        wc.getMovies("https://www.imdb.com/chart/top/",5,2);


    }

}
