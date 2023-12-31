package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();

        int totalAmountPaid = 0;
        if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.BASIC)
            totalAmountPaid = 500 + 200 * subscriptionEntryDto.getNoOfScreensRequired();
        else if(subscriptionEntryDto.getSubscriptionType() == SubscriptionType.PRO)
            totalAmountPaid = 800 + 250 * subscriptionEntryDto.getNoOfScreensRequired();
        else
            totalAmountPaid = 1000 + 350 * subscriptionEntryDto.getNoOfScreensRequired();

        Subscription subscription = new Subscription(subscriptionEntryDto.getSubscriptionType(),
                subscriptionEntryDto.getNoOfScreensRequired(), subscriptionEntryDto.getStartSubscriptionDate(),
                totalAmountPaid);

        subscription.setUser(user);

        user.setSubscription(subscription);

        userRepository.save(user);

        return totalAmountPaid;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        User user = userRepository.findById(userId).get();

        int diffInFare = 0;
        Subscription subscription = user.getSubscription();

        if(subscription.getSubscriptionType() == SubscriptionType.ELITE)
            throw new Exception("Already the best Subscription");


        else if(subscription.getSubscriptionType() == SubscriptionType.BASIC)
        {
            diffInFare = 800 + 250 * subscription.getNoOfScreensSubscribed() - subscription.getTotalAmountPaid();
            SubscriptionEntryDto subscriptionEntryDto = new SubscriptionEntryDto(userId, SubscriptionType.PRO, new Date(),
                                         subscription.getNoOfScreensSubscribed());
            buySubscription(subscriptionEntryDto);
        }

        else
        {
            diffInFare = 1000 + 350 * subscription.getNoOfScreensSubscribed() - subscription.getTotalAmountPaid();
            SubscriptionEntryDto subscriptionEntryDto = new SubscriptionEntryDto(userId, SubscriptionType.ELITE, new Date(),
                    subscription.getNoOfScreensSubscribed());
            buySubscription(subscriptionEntryDto);
        }

        return diffInFare;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        int totalRevenue = 0;
        for(Subscription subscription : subscriptions)
        {
            totalRevenue += subscription.getTotalAmountPaid();
        }

        return totalRevenue;
    }

}
