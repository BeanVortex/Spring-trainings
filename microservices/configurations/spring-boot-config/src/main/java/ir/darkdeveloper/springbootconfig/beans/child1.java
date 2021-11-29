package ir.darkdeveloper.springbootconfig.beans;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class child1 implements parent {
}
