package org.easyrules.samples.shop.part2;

import javax.management.MXBean;
import org.easyrules.api.JmxRule;

@MXBean
public interface AgeJmxRule extends JmxRule {
    
    int getAdultAge();
    
    void setAdultAge(int adultAge);

}
