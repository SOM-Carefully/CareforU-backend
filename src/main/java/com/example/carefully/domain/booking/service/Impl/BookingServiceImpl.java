package com.example.carefully.domain.booking.service.Impl;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.repository.BookingRepository;
import com.example.carefully.domain.booking.service.BookingService;
import com.example.carefully.domain.user.entity.General;
import com.example.carefully.domain.user.entity.Operation;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public void request(BookingDto.ReceiveRequest receiveRequest) {
        General general = (General) getCurrentUser(userRepository);
        Booking booking = Booking.request(general, receiveRequest);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto.ServiceResponse lookup(Long bookingId) {
        Booking booking = bookingRepository.getReferenceById(bookingId);
        return BookingDto.ServiceResponse.create(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto.ServiceResponse> userLookup() {
        User user = getCurrentUser(userRepository);
        List<BookingDto.ServiceResponse> bookingList = bookingRepository.getBookingsByGeneralOrderByByCreatedAtDest((General) user);
        return bookingList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto.ServiceResponse> operationLookup() {
        User user = getCurrentUser(userRepository);
        List<BookingDto.ServiceResponse> bookingList = bookingRepository.getBookingsByOperationOrderByByCreatedAtDest((Operation) user);
        return bookingList;
    }


}