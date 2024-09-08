CREATE TABLE chat_service.attachment
(
    id              VARCHAR(36) NOT NULL,
    file_url        VARCHAR(80) NULL,
    created_at      datetime    NOT NULL,
    updated_at      datetime    NOT NULL,
    message_id      BIGINT      NOT NULL,
    conversation_id BIGINT      NOT NULL,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

CREATE TABLE chat_service.conversation
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    title             VARCHAR(40) NULL,
    type              SMALLINT     NOT NULL,
    created_at        datetime     NOT NULL,
    updated_at        datetime     NOT NULL,
    message_count     BIGINT       NOT NULL,
    participant_count INT          NOT NULL,
    creator_id        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_conversation PRIMARY KEY (id)
);

CREATE TABLE chat_service.message
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    type            SMALLINT     NOT NULL,
    text            VARCHAR(255) NULL,
    created_at      datetime     NOT NULL,
    updated_at      datetime     NOT NULL,
    sender_id       VARCHAR(255) NOT NULL,
    conversation_id BIGINT       NOT NULL,
    CONSTRAINT pk_message PRIMARY KEY (id)
);

CREATE TABLE chat_service.participant
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    type            SMALLINT     NOT NULL,
    created_at      datetime     NOT NULL,
    updated_at      datetime     NOT NULL,
    conversation_id BIGINT       NOT NULL,
    user_id         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_participant PRIMARY KEY (id)
);

CREATE TABLE chat_service.user
(
    id         VARCHAR(255) NOT NULL,
    status     SMALLINT     NOT NULL,
    created_at datetime     NOT NULL,
    updated_at datetime     NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE chat_service.attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES chat_service.conversation (id);

ALTER TABLE chat_service.attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_MESSAGE FOREIGN KEY (message_id) REFERENCES chat_service.message (id);

ALTER TABLE chat_service.conversation
    ADD CONSTRAINT FK_CONVERSATION_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES chat_service.user (id);

ALTER TABLE chat_service.message
    ADD CONSTRAINT FK_MESSAGE_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES chat_service.conversation (id);

ALTER TABLE chat_service.message
    ADD CONSTRAINT FK_MESSAGE_ON_SENDER FOREIGN KEY (sender_id) REFERENCES chat_service.user (id);

ALTER TABLE chat_service.participant
    ADD CONSTRAINT FK_PARTICIPANT_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES chat_service.conversation (id);

ALTER TABLE chat_service.participant
    ADD CONSTRAINT FK_PARTICIPANT_ON_USER FOREIGN KEY (user_id) REFERENCES chat_service.user (id);