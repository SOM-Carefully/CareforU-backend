package com.example.carefully.domain.booking.service.Impl;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.booking.exception.NotValidationBookingId;
import com.example.carefully.domain.booking.exception.NotValidationServiceAdmin;
import com.example.carefully.domain.booking.repository.*;
import com.example.carefully.domain.booking.service.BookingService;
import com.example.carefully.domain.booking.entity.Communication;
import com.example.carefully.domain.booking.entity.Dwelling;
import com.example.carefully.domain.booking.entity.Education;
import com.example.carefully.domain.booking.entity.Traffic;
import com.example.carefully.domain.bookingRequest.entity.BookingRequest;
import com.example.carefully.domain.bookingRequest.repository.BookingRequestRepository;
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
    private final BookingRequestRepository bookingRequestRepository;
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;
    private final CommunicationRepository communicationRepository;
    private final TrafficRepository trafficRepository;
    private final DwellingRepository dwellingRepository;

    /*
    서비스 신청
     */
    @Override
    @Transactional
    public void educationRequest(BookingDto.EducationReceiveRequest edr) {
        Education booking = Education.educationRequest(edr);
        User cu = getCurrentUser(userRepository);
        BookingRequest bookingRequest = BookingRequest.educationBookingRequest(booking, cu, edr);
        bookingRequestRepository.save(bookingRequest);
    }

    @Override
    @Transactional
    public void trafficRequest(BookingDto.TrafficReceiveRequest trr) {
        Traffic booking = Traffic.trafficRequest(trr);
        User cu = getCurrentUser(userRepository);
        BookingRequest bookingRequest = BookingRequest.trafficBookingRequest(booking, cu, trr);
        bookingRequestRepository.save(bookingRequest);
    }

    @Override
    @Transactional
    public void dwellingRequest(BookingDto.DwellingReceiveRequest drr) {
        Dwelling booking = Dwelling.dwellingRequest(drr);
        User currentUser = getCurrentUser(userRepository);
        BookingRequest bookingRequest = BookingRequest.dwellingBookingRequest(booking, currentUser, drr);
        bookingRequestRepository.save(bookingRequest);
    }

    @Override
    @Transactional
    public void communicationRequest(BookingDto.CommunicationReceiveRequest crr) {
        User currentUser = getCurrentUser(userRepository);
        Communication booking = Communication.communicationRequest(crr);
        BookingRequest bookingRequest = BookingRequest.communicationBookingRequest(booking, currentUser, crr);
        bookingRequestRepository.save(bookingRequest);
    }

    /*
    서비스 신청 전체 리스트 조회
    */
    @Override
    @Transactional(readOnly = true)
    public SliceDto<BookingDto.ServiceAllResponse> serviceAllLookup() {
        Slice<BookingRequest> bookingList = bookingRequestRepository.findAllByOrderByCreatedAtDesc();
        return SliceDto.create(bookingList.map(BookingDto.ServiceAllResponse::create));
    }

    /*
    단일 서비스 조회
     */
    @Override
    @Transactional(readOnly = true)
    public BookingDto.EducationReceiveResponse educationLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        // 아마 같은 값이긴 할 거 같은데 혹시 몰라소..
        Education booking = educationRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.EducationReceiveResponse.create(bookingRequest, booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto.CommunicationReceiveResponse communicationLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        Communication booking = communicationRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.CommunicationReceiveResponse.create(bookingRequest, booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto.TrafficReceiveResponse trafficLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        Traffic booking = trafficRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.TrafficReceiveResponse.create(bookingRequest, booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDto.DwellingReceiveResponse dwellingLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        Dwelling booking = dwellingRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.DwellingReceiveResponse.create(bookingRequest, booking);
    }

    /*
    내 서비스 리스트 조회
     */
    @Override
    public SliceDto<BookingDto.ServiceAllResponse> userLookup() {
        Slice<BookingDto.ServiceAllResponse> bookingList = null;
        User currentUser = getCurrentUser(userRepository);

        if (currentUser.getRole().toString().equals("ADMIN")) {
            bookingList = bookingRequestRepository.findAllByAdmin(currentUser).map(BookingDto.ServiceAllResponse::create);
        } else {
            bookingList = bookingRequestRepository.findAllByUser(currentUser).map(BookingDto.ServiceAllResponse::create);
        }
        return SliceDto.create(bookingList);
    }

    /*
    서비스 승인
     */
    @Override
    @Transactional
    public void accept(Long bookingId, BookingDto.ServiceAcceptRequest serviceAcceptRequest) {
        BookingRequest bookingRequest = bookingRequestRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        User currentUser = getCurrentUser(userRepository);

        bookingRequest = checkAcceptAdmin(bookingRequest, currentUser);
        bookingRequest.accept(serviceAcceptRequest);
        bookingRequestRepository.save(bookingRequest);
    }

    /*
    서비스 진행 중
     */
    @Override
    @Transactional
    public void ongoing(Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        User currentUser = getCurrentUser(userRepository);

        bookingRequest = checkAcceptAdmin(bookingRequest, currentUser);
        bookingRequest.ongoing();
        bookingRequestRepository.save(bookingRequest);
    }

    /*
    서비스 취소
     */
    @Override
    @Transactional
    public void cancel(Long bookingId, BookingDto.ServiceRejectRequest serviceRejectRequest) {
        BookingRequest bookingRequest = bookingRequestRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        User currentUser = getCurrentUser(userRepository);

        bookingRequest = checkAcceptAdmin(bookingRequest, currentUser);
        bookingRequest.cancel(serviceRejectRequest);
        bookingRequestRepository.save(bookingRequest);
    }

    /*
    서비스 완료
     */
    @Override
    @Transactional
    public void complete(Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.findById(bookingId).orElseThrow(NotValidationBookingId::new);
        User currentUser = getCurrentUser(userRepository);

        bookingRequest = checkAcceptAdmin(bookingRequest, currentUser);
        bookingRequest.complete();
        bookingRequestRepository.save(bookingRequest);
    }

    public boolean checkCurrentAdmin(BookingRequest bookingRequest, User currentUser) {
        if (bookingRequest.getAdmin() == currentUser) {
            return true;
        } else {
            throw new NotValidationServiceAdmin();
        }
    }

    public BookingRequest checkAcceptAdmin(BookingRequest bookingRequest, User user) {
        if (bookingRequest.getAdmin() == null) {
            bookingRequest.setAdmin(user);
        } else if (checkCurrentAdmin(bookingRequest, user)) {
            bookingRequest.setNullAdmin();
            checkAcceptAdmin(bookingRequest, user);
        }
            return bookingRequest;
    }
}