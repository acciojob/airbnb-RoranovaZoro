package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelManagementService {
    HotelManagementRepository repositoryObject = new HotelManagementRepository();
    public String addHotel(Hotel hotel)
    {
        return repositoryObject.addHotel(hotel);
    }
    public void addUser(User user)
    {
        repositoryObject.addUser(user);
    }
    public String getHotelWithMostFacilities()
    {
        HashMap<String, Hotel> hotelDb = repositoryObject.getHotelDb();
        if(hotelDb.size() == 0){ return ""; }
        int maxFacilities = 0;
        String name="";
        for(Hotel hotel: hotelDb.values()){
            if(maxFacilities<hotel.getFacilities().size()){
                maxFacilities=hotel.getFacilities().size();
                name=hotel.getHotelName();
            }else if(maxFacilities==hotel.getFacilities().size()){
                if(name==null){
                    name=hotel.getHotelName();
                }else if(name.compareTo(hotel.getHotelName())>0){
                    name=hotel.getHotelName();
                }
            }
        }
        return name;
    }
    public int bookARoom(Booking booking)
    {
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
        HashMap<String, Hotel> hotelDb = repositoryObject.getHotelDb();
        Hotel hotel = hotelDb.get(booking.getHotelName());
        if(booking.getNoOfRooms() > hotel.getAvailableRooms())
        {
            return -1;
        }
        else
        {
            hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());
            int totalAmountPaid = booking.getNoOfRooms() * hotel.getPricePerNight();
            booking.setAmountToBePaid(totalAmountPaid);
            repositoryObject.updateHotel(hotel);
            repositoryObject.addHotelAndBooking(booking);
            repositoryObject.addUserBooking(booking);
            return totalAmountPaid;
        }
    }
    public int getBookings(Integer aadharCard)
    {
        HashMap<Integer, List<Booking>> userBookingDb = repositoryObject.getUserBookingDb(aadharCard);
        if(userBookingDb.size() == 0) { return 0; }
        return userBookingDb.get(aadharCard).size();
    }
    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName)
    {
        HashMap<String, Hotel> hotelDb = repositoryObject.getHotelDb();
        Hotel hotel = hotelDb.get(hotelName);
        List<Facility> oldFacilities = hotel.getFacilities();
        for(Facility facility : newFacilities)
        {
            if(!oldFacilities.contains(facility))
            {
                oldFacilities.add(facility);
            }
        }
        hotel.setFacilities(oldFacilities);
        repositoryObject.updateHotel(hotel);
        return hotel;
    }
}