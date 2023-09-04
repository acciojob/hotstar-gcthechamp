package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo

        WebSeries webSeries = webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName());

        if(webSeries != null)
            throw new Exception("Series is already present");

        webSeries = new WebSeries(webSeriesEntryDto.getSeriesName(),webSeriesEntryDto.getAgeLimit(),
                webSeriesEntryDto.getRating(),webSeriesEntryDto.getSubscriptionType());

        ProductionHouse productionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();

        //Updating rating of production house
        List<WebSeries> webSeriesList = productionHouse.getWebSeriesList();
        double totalRating = (double)productionHouse.getRatings() * webSeriesList.size();
        double newRating = (double)(totalRating + webSeries.getRating())/(webSeriesList.size() + 1);
        productionHouse.setRatings(newRating);

        //Adding new web series to web series' list of this production house
        webSeriesList.add(webSeries);
        productionHouse.setWebSeriesList(webSeriesList);

        //Setting this production house as this web series' production house
        webSeries.setProductionHouse(productionHouse);

        productionHouseRepository.save(productionHouse);

        webSeries = webSeriesRepository.save(webSeries);

        return webSeries.getId();
    }

}
