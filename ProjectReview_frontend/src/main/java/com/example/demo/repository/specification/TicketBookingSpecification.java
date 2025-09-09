package com.example.demo.repository.specification;

import java.time.LocalDate;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.example.demo.entity.TicketBooking;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketBookingSpecification {

    public static Specification<TicketBooking> getStreamBySearchSpec(String searchParam) {
	return new Specification<TicketBooking>() {
	    /**
	     * 
	     */
	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings({ "unused" })
	    @Override
	    public Predicate toPredicate(Root<TicketBooking> root, CriteriaQuery<?> query,
		    CriteriaBuilder criteriaBuilder) {
		Predicate finalPredicate = null;
		JSONParser parser = new JSONParser();
		JSONObject searchObject;

		try {

		    if (StringUtils.hasLength(searchParam)) {
			searchObject = (JSONObject) parser.parse(searchParam);

			String bookingId = (String) searchObject.get("bookingId");
			String timeSlot = (String) searchObject.get("timeSlot");
			String customerName = (String) searchObject.get("customerName");
			String phnNo = (String) searchObject.get("phnNo");
			String status = (String) searchObject.get("status");
			String startDate = (String) searchObject.get("startDate");
			String endDate = (String) searchObject.get("endDate");

			if (StringUtils.hasLength(bookingId)) {
			    Predicate predicate = criteriaBuilder.like(root.get("id").get("bookingId"),
				    "%" + bookingId + "%");
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }
			}

			if (StringUtils.hasLength(customerName)) {
			    Predicate predicate = criteriaBuilder.like(root.get("id").get("customerName"),
				    "%" + customerName + "%");
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }
			}
			
			if (StringUtils.hasLength(phnNo)) {
			    Predicate predicate = criteriaBuilder.like(root.get("id").get("phnNo"),
				    "%" + phnNo + "%");
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }
			}

			if (StringUtils.hasLength(timeSlot)) {
			    Predicate predicate = criteriaBuilder.equal(root.get("timeSlot"), timeSlot);
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }
			}

			if (StringUtils.hasLength(startDate) && StringUtils.hasLength(endDate)) {
			    LocalDate start = LocalDate.parse(startDate);
			    LocalDate end = LocalDate.parse(endDate);
			    Predicate predicate = criteriaBuilder.between(root.get("bookingDate"), start, end);
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }

			} else if (StringUtils.hasLength(startDate)) {
			    LocalDate start = LocalDate.parse(startDate);
			    Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("bookingDate"), start);
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }
			} else if (StringUtils.hasLength(endDate)) {
			    LocalDate end = LocalDate.parse(endDate);
			    Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("bookingDate"), end);
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }

			}
			if (StringUtils.hasLength(status)) {
			    Predicate predicate = criteriaBuilder.equal(root.get("status"), status);
			    if (finalPredicate != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, predicate);
			    } else {
				finalPredicate = criteriaBuilder.and(predicate);
			    }
			}

		    }
		    query.orderBy(criteriaBuilder.desc(root.get("createdTime")));

		} catch (ParseException e) {
		    log.error("Error : " + e.getMessage());
		}

		return finalPredicate;
	    }
	};
    }

}
