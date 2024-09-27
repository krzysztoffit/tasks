package com.crud.tasks.trello.client.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrelloValidatorTest {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    void shouldValidateTrelloBoardsWithFilteredBoardsTest() {
        // given
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(new TrelloBoard(
                "1",
                "test",
                List.of(new TrelloList("1", "First list", false))
        ));

        // when
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(boards);

        // then
        assertEquals(0, filteredBoards.size());
    }
}
