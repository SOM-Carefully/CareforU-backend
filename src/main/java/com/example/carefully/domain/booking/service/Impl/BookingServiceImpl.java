package com.example.carefully.domain.booking.service.Impl;

import com.example.carefully.domain.booking.entity.*;
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
    private final UserRepository userRepository;

    /*
    서비스 신청
     */
    @Override
    @Transactional
    public void educationRequest(BookingDto.EducationReceiveRequest educationReceiveRequest) {
        User currentUser = getCurrentUser(userRepository);
        Education booking = Education.educationRequest(currentUser, educationReceiveRequest);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void trafficRequest(BookingDto.TrafficReceiveRequest trafficReceiveRequest) {
        User currentUser = getCurrentUser(userRepository);
        Traffic booking = Traffic.trafficRequest(currentUser, trafficReceiveRequest);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void dwellingRequest(BookingDto.DwellingReceiveRequest dwellingReceiveRequest) {
        User currentUser = getCurrentUser(userRepository);
        Dwelling booking = Dwelling.dwellingRequest(currentUser, dwellingReceiveRequest);
        bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public void communicationRequest(BookingDto.CommunicationReceiveRequest communicationReceiveRequest) {
        User currentUser = getCurrentUser(userRepository);
        Communication booking = Communication.communicationRequest(currentUser, communicationReceiveRequest);
        bookingRepository.save(booking);
    }

    /*
    서비스 신청 전체 리스트 조회
    */
    @Override
    @Transactional(readOnly = true)
    public SliceDto<BookingDto.ServiceAllResponse> serviceAllLookup() {
        Slice<Booking> bookingList = bookingRepository.findAllByOrderByCreatedAtDesc();
        return SliceDto.create(bookingList.map(BookingDto.ServiceAllResponse::create));
    }

    /*
    단일 서비스 조회
     */
    @Override
    @Transactional(readOnly = true)
    public BookingDto.EducationReceiveResponse educationLookup (Long bookingId) {
        Education booking = (Education) bookingRepository.getReferenceById(bookingId);
        return BookingDto.EducationReceiveResponse.create(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto.CommunicationReceiveResponse communicationLookup (Long bookingId) {
        Communication booking = (Communication) bookingRepository.getReferenceById(bookingId);
        return BookingDto.CommunicationReceiveResponse.create(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto.TrafficReceiveResponse trafficLookup (Long bookingId) {
        Traffic booking = (Traffic) bookingRepository.getReferenceById(bookingId);
        return BookingDto.TrafficReceiveResponse.create(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto.DwellingReceiveResponse dwellingLookup (Long bookingId) {
        Dwelling booking = (Dwelling) bookingRepository.getReferenceById(bookingId);
        return BookingDto.DwellingReceiveResponse.create(booking);
    }

    /*
    내 서비스 리스트 조회
     */
    @Override
    public SliceDto<BookingDto.ServiceAllResponse> userLookup() {
        Slice<BookingDto.ServiceAllResponse> bookingList = null;
        User currentUser = getCurrentUser(userRepository);

        if (currentUser.getRole().toString().equals("ADMIN")) {
            bookingList = bookingRepository.findAllByAdmin(currentUser).map(BookingDto.ServiceAllResponse::create);
        } else {
            bookingList = bookingRepository.findAllByUser(currentUser).map(BookingDto.ServiceAllResponse::create);
        }
        return SliceDto.create(bookingList);
    }

    /*
    서비스 승인
     */
    @Override
    @Transactional
    public void accept(Long bookingId, BookingDto.ServiceAcceptRequest serviceAcceptRequest) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        User currentUser = getCurrentUser(userRepository);

        if (booking.getAdmin() == null) {
            booking.setAdmin(currentUser);
            booking.accept(serviceAcceptRequest);
            bookingRepository.save(booking);
        } else if (checkCurrentAdmin(booking, currentUser)) {
            booking.setNullAdmin();
            accept(bookingId, serviceAcceptRequest);
        }
    }

    /*
    서비스 취소
     */
    @Override
    @Transactional
    public void cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        User currentUser = getCurrentUser(userRepository);

        if (booking.getAdmin() == null) {
            booking.setAdmin(currentUser);
            booking.cancel();
            bookingRepository.save(booking);
        } else if (checkCurrentAdmin(booking, currentUser)) {
            booking.setNullAdmin();
            cancel(bookingId);
        }
    }

    /*
    서비스 완료
     */
    @Override
    @Transactional
    public void complete(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        User currentUser = getCurrentUser(userRepository);

        if (booking.getAdmin() == null) {
            booking.setAdmin(currentUser);
            booking.complete();
            bookingRepository.save(booking);
        } else if (checkCurrentAdmin(booking, currentUser)) {
            booking.setNullAdmin();
            complete(bookingId);
        }
    }

    public boolean checkCurrentAdmin(Booking booking, User currentUser) {
        if (booking.getAdmin() == currentUser) {
            return true;
        } else {
            throw new NotValidationServiceAdmin();
        }
    }
}