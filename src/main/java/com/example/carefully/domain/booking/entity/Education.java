package com.example.carefully.domain.booking.entity;

import com.example.carefully.domain.booking.dto.BookingDto;
import com.example.carefully.domain.bookingRequest.entity.BookingRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity(name="Education")
@DiscriminatorValue("Education")
@NoArgsConstructor(access = PROTECTED)
public class Education extends Booking {
    private Degree degree;
    private EducationContent educationContent;

    @Builder
    public Education(Long id, Degree degree, EducationContent educationContent) {
        super(id);
        this.degree = degree;
        this.educationContent = educationContent;
    }

    public static Education educationRequest(BookingDto.EducationReceiveRequest educationReceiveRequest) {
        return Education.builder()
                .degree(Degree.valueOf(educationReceiveRequest.getDegreeRequest().name()))
                .educationContent(EducationContent.valueOf(educationReceiveRequest.getEducationContentRequest().name()))
                .build();
    }
}

