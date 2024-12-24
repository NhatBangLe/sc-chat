ALTER TABLE chat_service.conversation
    ADD last_message_id BIGINT NULL;

ALTER TABLE chat_service.conversation
    ADD CONSTRAINT uc_conversation_last_message UNIQUE (last_message_id);

ALTER TABLE chat_service.conversation
    ADD CONSTRAINT FK_CONVERSATION_ON_LAST_MESSAGE FOREIGN KEY (last_message_id) REFERENCES chat_service.message (id);