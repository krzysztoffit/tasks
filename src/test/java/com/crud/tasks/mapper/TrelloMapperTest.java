package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void mapToBoardsTest() {
        // given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto(
                "1",
                "First board",
                List.of(new TrelloListDto("1", "First list", false))
        ));

        // when
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        // then

        assertEquals("First board", trelloBoards.get(0).getName());
    }

    @Test
    void mapToBoardsDtoTest() {
        // given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard(
                "2",
                "Second board",
                List.of(new TrelloList("2", "Second list", false))
        ));

        // when
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);

        // then
        assertEquals("Second board", trelloBoardDtos.get(0).getName());
    }

    @Test
    void mapToListTest() {
        // given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto(
                "1",
                "First list",
                false
        ));

        // when
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        // then
        assertEquals("First list", trelloLists.get(0).getName());
    }

    @Test
    void mapToListDtoTest() {
        // given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList(
                "2",
                "Second list",
                false
        ));

        // when
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        // then
        assertEquals("Second list", trelloListDtos.get(0).getName());
    }

    @Test
    void mapToCardTest() {
        // given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "First card",
                "Description of this first card",
                "1",
                "10"
        );

        // when
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        // then
        assertEquals("First card", trelloCard.getName());
    }

    @Test
    void mapToCardDtoTest() {
        // given
        TrelloCard trelloCard = new TrelloCard(
                "Second card",
                "Description of this second card",
                "2",
                "20"
        );

        // when
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // then
        assertEquals("Second card", trelloCardDto.getName());
    }
}
