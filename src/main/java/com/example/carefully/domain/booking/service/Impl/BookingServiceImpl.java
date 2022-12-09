package com.example.carefully.domain.booking.service.Impl;

import com.example.carefully.domain.booking.entity.Booking;
import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.exception.AlreadyProcessedService;
import com.example.carefully.domain.booking.exception.NotValidationBookingId;
import com.example.carefully.domain.booking.exception.NotValidationServiceAdmin;
import com.example.carefully.domain.booking.repository.BookingRepository;
import com.example.carefully.domain.booking.service.BookingService;
import com.example.carefully.domain.user.entity.User;
import com.example.carefully.domain.user.repository.UserRepository;
import com.example.carefully.global.dto.SliceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.carefully.global.utils.UserUtils.getCurrentUser;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    /*
    서비스 신청
     */
    @Override
    @Transactional
    public void request(BookingDto.ReceiveRequest receiveRequest) {
        User user = getCurrentUser();
        Booking booking = Booking.request(user, receiveRequest);
        bookingRepository.save(booking);
    }

    /*
    단일 서비스 조회
     */
    @Override
    @Transactional(readOnly = true)
    public BookingDto.ServiceResponse lookup(Long bookingId) {
        Booking booking = bookingRepository.getReferenceById(bookingId);
        return BookingDto.ServiceResponse.create(booking);
    }

    /*
    내 서비스 리스트 조회
     */
    @Override
    public SliceDto<BookingDto.ServiceResponse> userLookup() {
        User user = getCurrentUser();
        Slice<BookingDto.ServiceResponse> bookingList = null;
        if (user.getRole().toString().equals("ADMIN")) {
            bookingList = bookingRepository.findAllByAdmin(user).map(BookingDto.ServiceResponse::create);
        } else {
            bookingList = bookingRepository.findAllByUser(user).map(BookingDto.ServiceResponse::create);
        }
        return SliceDto.create(bookingList);
    }

    /*
    서비스 내용 수정
     */
    @Override
    @Transactional
    public void update(Long bookingId, BookingDto.UpdateRequest updateRequest) {
        User user = getCurrentUser();
        Booking booking = bookingRepository.findByIdAndUser(bookingId, user);
        booking.update(updateRequest.getRequestTime(), updateRequest.getBusinessType(), updateRequest.getContent());
        bookingRepository.save(booking);
    }

    /*
    서비스 승인
     */
    @Override
    @Transactional
    public void accept(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        if (booking.getAdmin() == null) {
            booking.setAdmin();
            booking.accept();
            bookingRepository.save(booking);
        } else if (checkCurrentAdmin(booking)) {
            accept(bookingId);
        } else {
            throw new AlreadyProcessedService();
        }
    }

    /*
    서비스 취소
     */
    @Override
    @Transactional
    public void cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        if (booking.getAdmin() == null) {
            booking.setAdmin();
            booking.cancel();
            bookingRepository.save(booking);
        } else if (checkCurrentAdmin(booking)) {
            cancel(bookingId);
        } else {
            throw new AlreadyProcessedService();
        }
    }

    /*
    서비스 완료
     */
    @Override
    @Transactional
    public void complete(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        if (booking.getAdmin() == null) {
            booking.setAdmin();
            booking.complete();
            bookingRepository.save(booking);
        } else if (checkCurrentAdmin(booking)) {
            complete(bookingId);
        } else {
            throw new AlreadyProcessedService();
        }
    }

    public boolean checkCurrentAdmin(Booking booking) {
        if (booking.getAdmin() == getCurrentUser()) {
            booking.setNullAdmin();
            return true;
        } else {
            throw new NotValidationServiceAdmin();
        }
    }
}