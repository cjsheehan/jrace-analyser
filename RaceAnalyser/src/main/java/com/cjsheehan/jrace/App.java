package com.cjsheehan.jrace;

import com.cjsheehan.jrace.racing.Race;
import com.cjsheehan.jrace.scrape.rpost.Card;

public class App 
{
    public static void main( String[] args )
    {
        String url = "http://www.racingpost.com/horses2/cards/card.sd?race_id=657553&r_date=2016-09-13#raceTabs=sc_";
        Card card = new Card();
        Race race = card.scrape(url);
        System.out.println("Race: \n\t" + race.toString());
    }
}


