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

    /**
     * 교육 서비스를 신청한다.
     *
     * @param edr 서비스 신청 내용, 서비스 신청시 첨부 파일, 학위(MASTER/DOCTOR), 서비스 종류(CONSULTING/CORRECTION/TRANSLATION)
     */
    @Override
    @Transactional
    public void educationRequest(BookingDto.EducationReceiveRequest edr) {
        Education booking = Education.educationRequest(edr);
        User cu = getCurrentUser(userRepository);
        BookingRequest bookingRequest = BookingRequest.educationBookingRequest(booking, cu, edr);
        bookingRequestRepository.save(bookingRequest);
    }


    /**
     * 교통 서비스를 신청한다.
     *
     * @param trr 서비스 신청 내용, 서비스 신청시 첨부 파일, 차종(COMPACT/MIDSIZE/SUV), 금액
     */
    @Override
    @Transactional
    public void trafficRequest(BookingDto.TrafficReceiveRequest trr) {
        Traffic booking = Traffic.trafficRequest(trr);
        User cu = getCurrentUser(userRepository);
        BookingRequest bookingRequest = BookingRequest.trafficBookingRequest(booking, cu, trr);
        bookingRequestRepository.save(bookingRequest);
    }

    /**
     * 주거 서비스를 신청한다.
     *
     * @param drr 서비스 신청 내용, 서비스 신청시 첨부 파일, 거래 방식(CHARTER/MONTHLY), 주거 형태(ONEROOM/TWOROOM/TWOBAY)
     */
    @Override
    @Transactional
    public void dwellingRequest(BookingDto.DwellingReceiveRequest drr) {
        Dwelling booking = Dwelling.dwellingRequest(drr);
        User currentUser = getCurrentUser(userRepository);
        BookingRequest bookingRequest = BookingRequest.dwellingBookingRequest(booking, currentUser, drr);
        bookingRequestRepository.save(bookingRequest);
    }

    /**
     * 통신 서비스를 신청한다.
     *
     * @param crr 서비스 신청 내용, 서비스 신청시 첨부 파일, 휴대폰 기종, 유심칩 신청 여부(True/False)
     */
    @Override
    @Transactional
    public void communicationRequest(BookingDto.CommunicationReceiveRequest crr) {
        User currentUser = getCurrentUser(userRepository);
        Communication booking = Communication.communicationRequest(crr);
        BookingRequest bookingRequest = BookingRequest.communicationBookingRequest(booking, currentUser, crr);
        bookingRequestRepository.save(bookingRequest);
    }

    /**
     * 전체 서비스 신청 내역 리스트를 조회한다.
     *
     * @return 서비스 ID, 서비스 카테고리, 서비스를 신청한 유저의 이메일, 서비스를 처리한 어드민 이메일, 서비스 처리 상태, 서비스 생성일자 및 시간
     */
    @Override
    @Transactional(readOnly = true)
    public SliceDto<BookingDto.ServiceAllResponse> serviceAllLookup() {
        Slice<BookingRequest> bookingList = bookingRequestRepository.findAllByOrderByCreatedAtDesc();
        return SliceDto.create(bookingList.map(BookingDto.ServiceAllResponse::create));
    }

    /**
     * 교육 서비스 신청 내역을 상세 조회한다.
     * 서비스 카테고리가 EDUCATION이 아닐 경우 error
     *
     * @param bookingId 서비스 ID
     * @return 서비스 고유 번호, 서비스 카테고리, 서비스를 신청한 유저의 이메일, 서비스를 처리한 어드민 이메일, 학위(MASTER/DOCTOR), 교육 서비스 종류(CONSULTING/CORRECTION/TRANSLATION), 서비스 신청 내용, 서비스 승인 내용, 서비스 신청시 업로드한 파일 url, 서비스 승인시 업로드한 파일 url, 서비스 처리 상태(ACCEPT/WAITING/CANCEL/COMPLETE)
     */
    @Override
    @Transactional(readOnly = true)
    public BookingDto.EducationReceiveResponse educationLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        Education booking = educationRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.EducationReceiveResponse.create(bookingRequest, booking);
    }

    /**
     * 통신 서비스 신청 내역을 상세 조회한다.
     * 서비스 카테고리가 COMMUNICATION이 아닐 경우 error
     *
     * @param bookingId 서비스 ID
     * @return 서비스 고유 번호, 서비스 카테고리, 서비스를 신청한 유저의 이메일, 서비스를 처리한 어드민 이메일, 휴대폰 기종, 유심 여부(True, False), 서비스 신청 내용, 서비스 승인 내용, 서비스 신청시 업로드한 파일 url, 서비스 승인시 업로드한 파일 url, 서비스 처리 상태(ACCEPT/WAITING/CANCEL/COMPLETE)
     */
    @Override
    @Transactional(readOnly = true)
    public BookingDto.CommunicationReceiveResponse communicationLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        Communication booking = communicationRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.CommunicationReceiveResponse.create(bookingRequest, booking);
    }

    /**
     * 교통 서비스 신청 내역을 상세 조회한다.
     * 서비스 카테고리가 TRAFFIC이 아닐 경우 error
     *
     * @param bookingId 서비스 ID
     * @return 서비스 고유 번호, 서비스 카테고리, 서비스를 신청한 유저의 이메일, 서비스를 처리한 어드민 이메일, 차종(COMPACT/MIDSIZE/SUV), 금액, 서비스 신청 내용, 서비스 승인 내용, 서비스 신청시 업로드한 파일 url, 서비스 승인시 업로드한 파일 url, 서비스 처리 상태(ACCEPT/WAITING/CANCEL/COMPLETE)
     */
    @Override
    @Transactional(readOnly = true)
    public BookingDto.TrafficReceiveResponse trafficLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        Traffic booking = trafficRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.TrafficReceiveResponse.create(bookingRequest, booking);
    }

    /**
     * 주거 서비스 신청 내역을 상세 조회한다.
     * 서비스 카테고리가 DWELLING이 아닐 경우 error
     *
     * @param bookingId 서비스 ID
     * @return 서비스 고유 번호, 서비스 카테고리, 서비스를 신청한 유저의 이메일, 서비스를 처리한 어드민 이메일, 거래 방식(CHARTER/MONTHLY), 주거 형태(ONEROOM/TWOROOM/TWOBAY), 서비스 신청 내용, 서비스 승인 내용, 서비스 신청시 업로드한 파일 url, 서비스 승인시 업로드한 파일 url, 서비스 처리 상태(ACCEPT/WAITING/CANCEL/COMPLETE)
     */
    @Override
    @Transactional(readOnly = true)
    public BookingDto.DwellingReceiveResponse dwellingLookup (Long bookingId) {
        BookingRequest bookingRequest = bookingRequestRepository.getReferenceById(bookingId);
        Dwelling booking = dwellingRepository.getReferenceById(bookingRequest.getBooking().getId());
        return BookingDto.DwellingReceiveResponse.create(bookingRequest, booking);
    }

    /**
     * 일반 회원일 경우 - 내가 신청한 서비스 내역 리스트를 조회한다.
     * 어드민 회원일 경우 - 내가 처리한 서비스 내역 리스트를 조회한다.
     *
     * @return 서비스 ID, 서비스 카테고리, 서비스를 신청한 유저의 이메일, 서비스를 처리한 어드민 이메일, 서비스 처리 상태, 서비스 생성일자 및 시간
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

    /**
     * 서비스 상태를 승인으로 변경한다.
     * 어드민 회원이 아닐 경우 error.
     *
     * @param bookingId 서비스 ID, serviceRejectRequest 서비스 승인 내용, 서비스 승인시 첨부 파일
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

    /**
     * 서비스 상태를 진행 중으로 수정한다.
     * 어드민 회원이 아닐 경우 error.
     *
     * @param bookingId 서비스 ID
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

    /**
     * 서비스 상태를 거절으로 변경한다.
     * 어드민 회원이 아닐 경우 error.
     *
     * @param bookingId, serviceRejectRequest 서비스 거절 사유
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

    /**
     * 서비스 상태를 완료로 변경한다.
     * 어드민 회원이 아닐 경우 error.
     *
     * @param bookingId, serviceRejectRequest 서비스 거절 사유
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

    /**
     * 서비스 상태 수정시 기존에 처리한 어드민 회원과 동일한지 확인한다.
     *
     * @param bookingRequest 서비스 정보, currentUser 서비스 상태를 수정하려고 하는 유저
     * @return 기존에 서비스를 처리한 유저와 현재 유저가 같다면 true, 아니라면 error 반환
     */
    public boolean checkCurrentAdmin(BookingRequest bookingRequest, User currentUser) {
        if (bookingRequest.getAdmin() == currentUser) {
            return true;
        } else {
            throw new NotValidationServiceAdmin();
        }
    }


    /**
     * 서비스를 처리한 어드민이 널일 경우 해당 서비스를 처리한 어드민을 현재 유저로 설정한다.
     * 서비스를 처리한 어드민과 동일한 유저일 경우 처리한 어드민을 Null로 변경한다.
     * 서비스 상태 변경시 checkAcceptAdmin를 호출하여 다시 현재 유저로 해당 서비스를 처리한 어드민을 현재 유저로 설정하기 위함.
     *
     * @param bookingRequest 처리할 서비스 정보, user 현재 유저
     * @return bookingRequest 처리할 서비스 정보
     */
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