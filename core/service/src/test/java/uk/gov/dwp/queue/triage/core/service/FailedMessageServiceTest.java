package uk.gov.dwp.queue.triage.core.service;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import uk.gov.dwp.queue.triage.core.dao.FailedMessageDao;
import uk.gov.dwp.queue.triage.core.domain.FailedMessage;
import uk.gov.dwp.queue.triage.core.domain.StatusHistoryEvent;
import uk.gov.dwp.queue.triage.core.domain.StatusHistoryEventMatcher;
import uk.gov.dwp.queue.triage.id.FailedMessageId;

import java.time.Instant;

import static uk.gov.dwp.queue.triage.core.domain.StatusHistoryEvent.Status.DELETED;
import static uk.gov.dwp.queue.triage.core.domain.StatusHistoryEvent.Status.RESEND;

public class FailedMessageServiceTest {

    private static final FailedMessageId FAILED_MESSAGE_ID = FailedMessageId.newFailedMessageId();
    private static final Instant NOW = Instant.now();

    private final FailedMessageDao failedMessageDao = Mockito.mock(FailedMessageDao.class);

    private final FailedMessageService underTest = new FailedMessageService(failedMessageDao);
    private final FailedMessage failedMessage = Mockito.mock(FailedMessage.class);

    @Test
    public void createFailedMessageDelegatesToDao() {
        underTest.create(failedMessage);

        Mockito.verify(failedMessageDao).insert(failedMessage);
    }

    @Test
    public void updateStatus() throws Exception {
        underTest.updateStatus(FAILED_MESSAGE_ID, RESEND);

        Mockito.verify(failedMessageDao).updateStatus(
                Mockito.eq(FAILED_MESSAGE_ID),
                argThat(StatusHistoryEventMatcher.equalTo(RESEND).withUpdatedDateTime(Matchers.notNullValue(Instant.class)))
        );
    }

    @Test
    public void updateStatusWithGivenDate() {
        underTest.updateStatus(FAILED_MESSAGE_ID, new StatusHistoryEvent(RESEND, NOW));

        Mockito.verify(failedMessageDao).updateStatus(
                Mockito.eq(FAILED_MESSAGE_ID),
                argThat(StatusHistoryEventMatcher.equalTo(RESEND).withUpdatedDateTime(Matchers.equalTo(NOW)))
        );
    }

    @Test
    public void deleteFailedMessage() {
        underTest.delete(FAILED_MESSAGE_ID);

        Mockito.verify(failedMessageDao).updateStatus(
                Mockito.eq(FAILED_MESSAGE_ID),
                argThat(StatusHistoryEventMatcher.equalTo(DELETED).withUpdatedDateTime(Matchers.notNullValue(Instant.class)))
        );
    }

    public StatusHistoryEvent argThat(StatusHistoryEventMatcher matcher) {
        return Mockito.argThat(new HamcrestArgumentMatcher<>(matcher));
    }
}