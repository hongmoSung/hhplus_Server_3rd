package hhplus.serverjava.api.user.response;

import hhplus.serverjava.domain.pointhistory.entity.PointHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
public class PointHistoryDto {

    private List<PointHistoryList> pointHistoryList;

    public PointHistoryDto(List<PointHistory> pointHistories) {
        this.pointHistoryList = pointHistories.stream()
                .map(pointHistory -> new PointHistoryList(pointHistory))
                .collect(Collectors.toList());
    }

}
