package com.example.eventms.organizer.mapper;

import com.example.eventms.mbp.entity.*;
import com.example.eventms.organizer.constant.PayloadConstants;
import com.example.eventms.organizer.dto.EventContent.Module;
import com.example.eventms.organizer.dto.EventContent.Widget;
import com.example.eventms.organizer.dto.EventDetail;
import com.example.eventms.organizer.dto.EventPayload;
import com.example.eventms.organizer.dto.EventResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.mapstruct.Mapper;

import java.time.LocalTime;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;

import static com.example.eventms.organizer.dto.EventContent.ModuleType;
import static com.example.eventms.organizer.dto.EventContent.WidgetType;

/**
 * @author vicendy04
 * @since 2025-03
 */
@Mapper(componentModel = "spring")
public interface EventConverter {
    EesEvent toEntity(EventPayload.EventDto dto);

    EesTicket toEntity(EventPayload.TicketDto dto);

    EventResult.EventDto toEventResultDto(EesEvent entity);

    EventResult.TicketDto toTicketResultDto(EesTicket entity);

    EventDetail.EventDto toEventDetailDto(EesEvent entity);

    EventDetail.VenueDto toVenueDetailDto(EesVenue entity);

    EventDetail.OrganizerDto toOrganizerDetailDto(UesOrganizer entity);

    EventDetail.CheckoutSettingDto toCheckoutSettingDto(EesCheckoutSetting entity);

    List<EventDetail.CheckoutSettingDto> toCheckoutSettingDtos(List<EesCheckoutSetting> entities);

    EventDetail.AttributeDto toAttrDetailDto(EesAttribute entity);

    EventDetail.AttributeValueDto toAttrValueDetailDto(EesAttributeValue entity);

    List<EventDetail.AttributeDto> toAttrDetailDtos(List<EesAttribute> entities);

    List<EventDetail.AttributeValueDto> toAttrValueDetailDtos(List<EesAttributeValue> entities);

    default EesEvent toEventFrom(Long eventId, List<Module> modules) {
        EesEvent eesEvent = new EesEvent();

        for (Module m : modules) {
            ModuleType mType = m.getType();
            JsonNode mData = m.getData();

            if (mType == ModuleType.TEXT) {
                String title = mData.path(EesEvent.Fields.title).asText();
                String summary = mData.path(EesEvent.Fields.summary).asText();

                eesEvent.setTitle(title);
                eesEvent.setSummary(summary);
            } else if (mType == ModuleType.IMAGE) {
                JsonNode slides = mData.path(PayloadConstants.slides); // list
                for (JsonNode slide : slides) {
                    String pic = slide.path(EesEvent.Fields.pic).asText();
                    String albumPics = slide.path(EesEvent.Fields.albumPics).asText();

                    eesEvent.setPic(pic);
                    eesEvent.setAlbumPics(albumPics);
                }
            }
        }

        eesEvent.setId(eventId);

        return eesEvent;
    }

    default SimpleImmutableEntry<List<EesAgenda>, List<EesFaq>> toWidgetsFrom(Long eventId, List<Widget> widgets) {
        List<EesAgenda> eesAgendas = new ArrayList<>();
        List<EesFaq> eesFaqs = new ArrayList<>();

        for (Widget w : widgets) {
            WidgetType wType = w.getType();
            JsonNode wData = w.getData();

            if (wType == WidgetType.AGENDA) {
                JsonNode slots = wData.path(PayloadConstants.slots); // list
                // Todo: https://www.baeldung.com/java-jackson-jsonnode-collection#42-using-convertvalue
                for (JsonNode slot : slots) {
                    String sessionName = slot.path(EesAgenda.Fields.sessionName).asText();
                    String summary = slot.path(EesAgenda.Fields.summary).asText();
                    String hostName = slot.path(EesAgenda.Fields.hostName).asText();
                    LocalTime startTime = LocalTime.parse(slot.path(EesAgenda.Fields.startTime).asText());
                    LocalTime endTime = LocalTime.parse(slot.path(EesAgenda.Fields.endTime).asText());

                    EesAgenda eesAgenda = new EesAgenda();
                    eesAgenda.setEventId(eventId);
                    eesAgenda.setSessionName(sessionName);
                    eesAgenda.setSummary(summary);
                    eesAgenda.setHostName(hostName);
                    eesAgenda.setStartTime(startTime);
                    eesAgenda.setEndTime(endTime);
                    eesAgendas.add(eesAgenda);
                }
            } else if (wType == WidgetType.FAQS) {
                JsonNode faqs = wData.path(PayloadConstants.faqs); // list
                for (JsonNode faq : faqs) {
                    String question = faq.path(EesFaq.Fields.question).asText();
                    String answer = faq.path(EesFaq.Fields.answer).asText();

                    EesFaq eesFaq = new EesFaq();
                    eesFaq.setEventId(eventId);
                    eesFaq.setQuestion(question);
                    eesFaq.setAnswer(answer);
                    eesFaqs.add(eesFaq);
                }
            }
        }

        return new SimpleImmutableEntry<>(eesAgendas, eesFaqs);
    }
}
