package com.crmApp.API.controller;

import com.crmApp.API.repository.ClientRepository;
import com.crmApp.API.repository.UserRepository;

import com.crmApp.API.model.User;

import java.util.NoSuchElementException;

import com.crmApp.API.model.Client;
import com.crmApp.API.model.Order;
import com.crmApp.API.model.Product;
import com.crmApp.API.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping(value = "/stats", produces = {MediaType.APPLICATION_JSON_VALUE})
public class StatsRESTController {
    @Autowired
    private UserRepository userRepo;
    // private OrderRepository orderRepo;
    private ClientRepository clientRepo;

    @GetMapping("/most-sold/{userId}")
    Product getMostSoldProduct(@PathVariable Long userId) {
        long max = 0;
        Product maxProduct = new Product();
        long nbSold;
        try {
            User sampleUser = userRepo.findById(userId).get();
            for (Product p: sampleUser.getProducts()) {
                nbSold = p.getSold();
                if (nbSold > max) {
                    max = nbSold;
                    maxProduct = p;
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("Wrong User id");
        }
        return maxProduct;
    }

    @GetMapping("/less-sold/{userId}")
    Product getLessSoldProduct(@PathVariable Long userId) {
        long min = 9_223_372_036_854_775_807L;
        Product minProduct = new Product();
        long nbSold;
        try {
            User sampleUser = userRepo.findById(userId).get();
            for (Product p: sampleUser.getProducts()) {
                nbSold = p.getSold();
                if (nbSold < min) {
                    min = nbSold;
                    minProduct = p;
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("Wrong User id");
        }
        return minProduct;
    }

    @GetMapping("/turnover/{userId}")
    double getTurnoverByUser(@PathVariable Long userId) {
        double count = 0;
        try {
            User sampleUser = userRepo.findById(userId).get();
            for (Client c: sampleUser.getClients()) {
                for (Order o: c.getOrder()) {
                    for (Transaction t: o.getTransactions()){
                        count += t.getQuantity() * t.getPrice();
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("Wrong User id");
        }
        return count;
    }

    @GetMapping("/turnover/{clientId}")
    double getTurnoverByClient(@PathVariable Long clientId) {
        double count = 0;
        try {
            Client sampleClient = clientRepo.findById(clientId).get();
                for (Order o: sampleClient.getOrder()) {
                    for (Transaction t: o.getTransactions()){
                        count += t.getQuantity() * t.getPrice();
                    }
                }
        } catch (NoSuchElementException e) {
            System.err.println("Wrong User id");
        }
        return count;
    }

    @GetMapping("/turnover/month/{monthInt}/{userId}")
    double getTurnoverByMonth(@PathVariable Long userId, @PathVariable Long clientId) {
        double count = 0;
        Calendar cal = Calendar.getInstance();
        Calendar orderCal = Calendar.getInstance();
        try {
            User sampleUser = userRepo.findById(userId).get();
            for (Client c: sampleUser.getClients()) {
                for (Order o: c.getOrder()) {
                    orderCal.setTime(o.getDatetime());
                    if (orderCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH))
                        for (Transaction t: o.getTransactions()){
                            count += t.getQuantity() * t.getPrice();
                        }
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("Wrong User id");
        }
        return count;
    }

    
    
    

}
