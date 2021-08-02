import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DictionaryConfig {
    @Bean
    public DictEnglish dictEnglish(){
        return new DictEnglish();
    }
    @Bean
    public DictNumeric dictNumeric(){
        return new DictNumeric();
    }
}
