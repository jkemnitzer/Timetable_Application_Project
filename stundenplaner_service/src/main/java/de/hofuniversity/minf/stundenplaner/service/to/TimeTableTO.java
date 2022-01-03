package de.hofuniversity.minf.stundenplaner.service.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableTO {

    private TimeTableVersionTO version;
    private List<LessonTO> lessons;

}
