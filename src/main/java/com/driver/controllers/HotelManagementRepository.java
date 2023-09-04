package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class HotelManagementRepository {
    HashMap<String, Hotel> hotelDb = new HashMap<>();
    HashMap<Integer, User> userDb = new HashMap<>();
    HashMap<String, Booking> bookingDb = new HashMap<>();
    HashMap<Integer, List<Booking>> userBookingDb = new HashMap<Integer, List<Booking>>();
    public String addHotel(Hotel hotel)
    {
        String hotelName = hotel.getHotelName();
        if(hotelDb.containsKey(hotelName))
        {
            return "FAILURE";
        }
        hotelDb.put(hotelName, hotel);
        return "SUCCESS";
    }
    public void addUser(User user)
    {
        userDb.put(user.getaadharCardNo(), user);
    }
    public HashMap<String, Hotel> getHotelDb()
    {
        return hotelDb;
    }
    public void updateHotel(Hotel hotel)
    {
        hotelDb.put(hotel.getHotelName(),hotel);
    }
    public void addHotelAndBooking(Booking booking)
    {
        bookingDb.put(booking.getBookingId(), booking);
    }
    public void addUserBooking(Booking booking)
    {
        int aadharCardNo = booking.getBookingAadharCard();
        List<Booking> bookings = userBookingDb.getOrDefault(aadharCardNo, new ArrayList<>());
        bookings.add(booking);
        userBookingDb.put(aadharCardNo, bookings);
    }
    public HashMap<Integer, List<Booking>> getUserBookingDb(Integer aadharCard)
    {
        return userBookingDb;
    }
}