CREATE TABLE player (
        id         BIGSERIAL PRIMARY KEY,
        puuid      VARCHAR(255) NOT NULL UNIQUE,
        name       VARCHAR(255) NOT NULL,
        tag        VARCHAR(255) NOT NULL,
        region     VARCHAR(10)  NOT NULL,
        wins       INT          NOT NULL DEFAULT 0,
        losses     INT          NOT NULL DEFAULT 0
);

CREATE TABLE match (
        id          VARCHAR(255) PRIMARY KEY,
        player_id   BIGINT       NOT NULL REFERENCES player(id),
        played_at   TIMESTAMPTZ  NOT NULL,
        map         VARCHAR(255) NOT NULL,
        queue_id    VARCHAR(255) NOT NULL,
        agent       VARCHAR(255) NOT NULL,
        kills       INT          NOT NULL,
        deaths      INT          NOT NULL,
        assists     INT          NOT NULL,
        won         BOOLEAN      NOT NULL
);

CREATE TABLE player_snapshot (
        id          BIGSERIAL    PRIMARY KEY,
        player_id   BIGINT       NOT NULL REFERENCES player(id),
        taken_at    TIMESTAMPTZ  NOT NULL,
        tier_name   VARCHAR(255) NOT NULL,
        rr          INT          NOT NULL,
        elo         INT          NOT NULL,
        wins        INT          NOT NULL,
        losses      INT          NOT NULL
);