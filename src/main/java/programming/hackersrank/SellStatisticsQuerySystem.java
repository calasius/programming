package programming.hackersrank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/** Created by claudio on 7/30/17. */
public class SellStatisticsQuerySystem {

  public static void main(String... args) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File("src/main/resources/hackersRankTests/olx2"));
    int lines = scanner.nextInt();

    DayCounter dayCounter = new DayCounter();

    for (int i = 0; i < lines; ++i) {
      String type = scanner.next("[S|Q]");
      if (type.equals("S")) {

        //day
        Integer day = scanner.nextInt();

        //product and category
        String pc = scanner.next("\\d+(\\.\\d+)?");
        String[] pr_cat = pc.split("\\.");
        Integer product = Integer.valueOf(pr_cat[0]);
        Integer cat = null;
        if (pr_cat.length > 1) {
          cat = Integer.valueOf(pr_cat[1]);
        }
        Optional<Integer> category = Optional.ofNullable(cat);

        //state and region
        String sr = scanner.next("\\d+(\\.\\d+)?");
        String[] st_reg = sr.split("\\.");
        Integer state = Integer.valueOf(st_reg[0]);
        Integer reg = null;
        if (st_reg.length > 1) {
          reg = Integer.valueOf(st_reg[1]);
        }
        Optional<Integer> region = Optional.ofNullable(reg);

        dayCounter.update(day, product, category, state, region);
      } else {
        String dayRange = scanner.next("\\d+(\\.\\d+)?");
        String[] days = dayRange.split("\\.");

        //product and category
        String pc = scanner.next("-1|\\d+(\\.\\d+)?");
        String[] pr_cat = pc.split("\\.");
        Integer product = Integer.valueOf(pr_cat[0]);
        Integer cat = null;
        if (pr_cat.length > 1) {
          cat = Integer.valueOf(pr_cat[1]);
        }
        Optional<Integer> category = Optional.ofNullable(cat);

        //state and region
        String sr = scanner.next("-1|\\d+(\\.\\d+)?");
        String[] st_reg = sr.split("\\.");
        Integer state = Integer.valueOf(st_reg[0]);
        Integer reg = null;
        if (st_reg.length > 1) {
          reg = Integer.valueOf(st_reg[1]);
        }
        Optional<Integer> region = Optional.ofNullable(reg);

        if (days.length == 1) {
          System.out.println(
              dayCounter.getCount(Integer.valueOf(days[0]), product, category, state, region));
        } else {
          Integer firstDay = Integer.valueOf(days[0]);
          Integer lastDay = Integer.valueOf(days[1]);
          int total = 0;
          for (int day = firstDay; day <= lastDay; ++day) {
            total += dayCounter.getCount(day, product, category, state, region);
          }
          System.out.println(total);
        }
      }
    }
  }
}

class DayCounter {
  public Map<Integer, Counter> counterByDay = new HashMap(100);
  public Map<Integer, ProductCounter> counterByDayAndProduct = new HashMap(100);

  public DayCounter() {
    for (int i = 1; i <= 100; ++i) {
      counterByDay.put(i, new Counter());
      counterByDayAndProduct.put(i, new ProductCounter());
    }
  }

  public void update(
      Integer day,
      Integer product,
      Optional<Integer> category,
      Integer state,
      Optional<Integer> region) {

    counterByDay.get(day).increment();
    counterByDayAndProduct.get(day).update(product, category, state, region);
  }

  public Integer getCount(
      Integer day,
      Integer product,
      Optional<Integer> category,
      Integer state,
      Optional<Integer> region) {

    if (product == -1 && state == -1) {
      return counterByDay.get(day).getCount();
    } else {
      return counterByDayAndProduct.get(day).getCount(product, category, state, region);
    }
  }
}

class ProductCounter {
  public Map<Integer, Counter> counterByProduct = new HashMap(20);
  public Map<Integer, CategoryCounter> counterByProductCategory = new HashMap(20);
  public Map<Integer, StateCounter> counterByProductState = new HashMap(20);
  public StateCounter stateCounter;

  public ProductCounter() {
    for (int i = 1; i <= 10; ++i) {
      counterByProduct.put(i, new Counter());
      counterByProductCategory.put(i, new CategoryCounter());
      counterByProductState.put(i, new StateCounter());
    }

    stateCounter = new StateCounter();
  }

  public void update(
      Integer product, Optional<Integer> category, Integer state, Optional<Integer> region) {
    counterByProduct.get(product).increment();
    if (category.isPresent()) {
      counterByProductCategory.get(product).update(category.get(), state, region);
    }
    counterByProductState.get(product).update(state, region);
    stateCounter.update(state, region);
  }

  public Integer getCount(
      Integer product, Optional<Integer> category, Integer state, Optional<Integer> region) {

    if (product == -1) {
      return stateCounter.getCount(state, region);
    } else if (state == -1) {
      return counterByProduct.get(product).getCount();
    } else if (!category.isPresent()) {
      return counterByProductState.get(product).getCount(state, region);
    } else {
      return counterByProductCategory.get(product).getCount(category.get(), state, region);
    }
  }
}

class CategoryCounter {
  public Map<Integer, Counter> counterByCategory = new HashMap(20);
  public Map<Integer, StateCounter> counterByCategoryState = new HashMap(20);

  public CategoryCounter() {
    for (int i = 1; i <= 4; ++i) {
      counterByCategory.put(i, new Counter());
      counterByCategoryState.put(i, new StateCounter());
    }
  }

  public void update(Integer category, Integer state, Optional<Integer> region) {
    counterByCategory.get(category).increment();
    counterByCategoryState.get(category).update(state, region);
  }

  public Integer getCount(Integer category, Integer state, Optional<Integer> region) {
    if (state == -1) {
      return counterByCategory.get(category).getCount();
    } else {
      return counterByCategoryState.get(category).getCount(state, region);
    }
  }
}

class StateCounter {
  public Map<Integer, Counter> counterByState = new HashMap(20);
  public Map<Integer, RegionCounter> counterByStateRegion = new HashMap(20);

  public StateCounter() {
    for (int i = 1; i <= 7; ++i) {
      counterByState.put(i, new Counter());
      counterByStateRegion.put(i, new RegionCounter());
    }
  }

  public Integer getCount(Integer state, Optional<Integer> region) {
    if (region.isPresent()) {
      return counterByStateRegion.get(state).getCount(region.get());
    } else {
      return counterByState.get(state).getCount();
    }
  }

  public void update(Integer state, Optional<Integer> region) {
    counterByState.get(state).increment();
    if (region.isPresent()) {
      counterByStateRegion.get(state).update(region.get());
    }
  }
}

class RegionCounter {
  Map<Integer, Counter> counterByRegion = new HashMap(20);

  public RegionCounter() {
    for (int i = 1; i <= 25; ++i) {
      counterByRegion.put(i, new Counter());
    }
  }

  public void update(Integer region) {
    counterByRegion.get(region).increment();
  }

  public Integer getCount(Integer region) {
    return counterByRegion.get(region).getCount();
  }
}

class Counter {
  private Integer count = 0;

  public void increment() {
    ++count;
  }

  public Integer getCount() {
    return count;
  }
}
