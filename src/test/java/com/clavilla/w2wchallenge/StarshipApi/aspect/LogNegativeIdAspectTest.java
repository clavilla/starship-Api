package com.clavilla.w2wchallenge.StarshipApi.aspect;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogNegativeIdAspectTest {

    @InjectMocks
    private LogNegativeIdAspect logNegativeIdAspect;

    @Mock
    private JoinPoint joinPoint;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<ILoggingEvent> captorLoggingEvent;

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(LogNegativeIdAspect.class);
        logger.addAppender(mockAppender);
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testLogIfNegativeId_withNegativeId_logsWarning() {
        // Simula un ID negativo
        logNegativeIdAspect.logIfNegativeId(joinPoint, -1L);

        // Verifica que el mensaje específico se haya logueado
        verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        ILoggingEvent loggedEvent = captorLoggingEvent.getValue();

        assertTrue(loggedEvent.getLevel().equals(Level.WARN) &&
                        loggedEvent.getFormattedMessage().contains("Attempted to access a starship with a negative ID: -1"),
                "Expected warning message not logged");
    }

}