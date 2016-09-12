package com.cjsheehan.jrace;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cjsheehan.jrace.racing.Horse;

public class App 
{
    public static void main( String[] args )
    {
        String url = "http://www.racingpost.com/horses2/cards/card.sd?race_id=657700&r_date=2016-09-12#raceTabs=sc_";
        
        Document doc = null;
        try
        {
            System.out.println("Fetching %s..." +  url);
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(3000)
                    .get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        Elements entrants = doc.select("table#sc_horseCard > tbody a > b");
        List<Horse> horses = new ArrayList<>();
        
        for(Element entrant: entrants) {
            Horse h = new Horse();
            h.setName(entrant.ownText());
            horses.add(h);
        }
        
        for (Horse horse : horses)
        {
            System.out.println(horse.toString());
        }
        
    }
}


