CREATE TABLE attachment
(
    id              VARCHAR(36) NOT NULL,
    created_at      datetime    NOT NULL,
    updated_at      datetime    NULL,
    message_id      BIGINT      NOT NULL,
    conversation_id BIGINT      NOT NULL,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

CREATE TABLE conversation
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime              NOT NULL,
    updated_at        datetime              NULL,
    is_read           BIT(1)                NOT NULL,
    title             VARCHAR(100)          NULL,
    type              SMALLINT              NOT NULL,
    message_count     BIGINT                NOT NULL,
    participant_count INT                   NOT NULL,
    creator_id        VARCHAR(36)           NOT NULL,
    CONSTRAINT pk_conversation PRIMARY KEY (id)
);

CREATE TABLE message
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    updated_at      datetime              NULL,
    type            SMALLINT              NOT NULL,
    text            VARCHAR(255)          NULL,
    sender_id       VARCHAR(36)           NOT NULL,
    conversation_id BIGINT                NOT NULL,
    CONSTRAINT pk_message PRIMARY KEY (id)
);

CREATE TABLE participant
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NOT NULL,
    updated_at      datetime              NULL,
    type            SMALLINT              NOT NULL,
    conversation_id BIGINT                NOT NULL,
    user_id         VARCHAR(36)           NOT NULL,
    CONSTRAINT pk_participant PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         VARCHAR(36) NOT NULL,
    created_at datetime    NOT NULL,
    updated_at datetime    NULL,
    status     SMALLINT    NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES conversation (id);

ALTER TABLE attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_MESSAGE FOREIGN KEY (message_id) REFERENCES message (id);

ALTER TABLE conversation
    ADD CONSTRAINT FK_CONVERSATION_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES user (id);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES conversation (id);

ALTER TABLE message
    ADD CONSTRAINT FK_MESSAGE_ON_SENDER FOREIGN KEY (sender_id) REFERENCES user (id);

ALTER TABLE participant
    ADD CONSTRAINT FK_PARTICIPANT_ON_CONVERSATION FOREIGN KEY (conversation_id) REFERENCES conversation (id);

ALTER TABLE participant
    ADD CONSTRAINT FK_PARTICIPANT_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);