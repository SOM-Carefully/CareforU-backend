package com.example.carefully.domain.booking.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity(name="Education")
@DiscriminatorValue("Education")
@NoArgsConstructor(access = PROTECTED)
public class Education extends Booking{
    private Degree degree;
    private EducationContent educationContent;

    @Builder
    public Education(Long id, String content, User user, User admin, BookingStatus bookingStatus, BusinessType businessType, String userFileUrl, String adminFileUrl, Degree degree, EducationContent educationContent) {
        super(id, content, user, admin, bookingStatus, businessType, userFileUrl, adminFileUrl);
        this.degree = degree;
        this.educationContent = educationContent;
    }

    public static Education educationRequest(User user, BookingDto.EducationReceiveRequest educationReceiveRequest) {
        return Education.builder()
                .user(user)
                .content(educationReceiveRequest.getContent())
                .bookingStatus(BookingStatus.valueOf("WAITING"))
                .businessType(BusinessType.valueOf("EDUCATION"))
                .degree(Degree.valueOf(educationReceiveRequest.getDegreeRequest().name()))
                .educationContent(EducationContent.valueOf(educationReceiveRequest.getEducationContentRequest().name()))
                .build();
    }
}

