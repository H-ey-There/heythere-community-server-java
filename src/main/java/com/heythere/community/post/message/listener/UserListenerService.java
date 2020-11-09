package com.heythere.community.post.message.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heythere.community.post.exception.ResourceNotFoundException;
import com.heythere.community.post.message.domain.UserEventDto;
import com.heythere.community.post.message.domain.UserMessageDto;
import com.heythere.community.post.model.User;
import com.heythere.community.post.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserListenerService {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Transactional
    public void processUpdateUserEvent(final ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        final UserEventDto userEvent = objectMapper.readValue(consumerRecord.value(), UserEventDto.class);
        log.info("User : {} ", userEvent);

        if(userEvent.getUserEventId()!=null && userEvent.getUserEventId()==000){
            throw new RecoverableDataAccessException("Temporary Network Issue");
        }

        if (!userRepository.existsById(userEvent.getUserEventId().longValue())) {
            final User newUser = save(userEvent);
            log.info("User saved : {} ", newUser);
        } else {
            final User updateUser = userRepository.findById(userEvent.getUserEventId().longValue())
                    .orElseThrow(() -> new ResourceNotFoundException("User","id", userEvent.getUserEventId().longValue()))
                    .update(userEvent);
            log.info("User updated: {} ", updateUser);
        }

    }

    @Transactional
    public void processDeleteUserEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        final UserEventDto userEvent = objectMapper.readValue(consumerRecord.value(), UserEventDto.class);
        userRepository.deleteById(userEvent.getUserEventId().longValue());
        log.info("User deleted ~ ");
    }

    private User save(final UserEventDto userEvent) {
        final UserMessageDto userMessage = userEvent.getUserMessageDto();

        return userRepository.save(User.builder()
                .id(userEvent.getUserEventId().longValue())
                .email(userMessage.getEmail())
                .name(userMessage.getName())
                .img(userMessage.getImg())
                .build());
    }
}
