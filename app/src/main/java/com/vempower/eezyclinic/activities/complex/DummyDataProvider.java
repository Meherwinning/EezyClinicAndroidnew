/*
 * Copyright 2017 Riyaz Ahamed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vempower.eezyclinic.activities.complex;

import android.graphics.Color;


import com.vempower.eezyclinic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyDataProvider {

  private static Random random = new Random();

  private static String[] articleDummyTimes = {
      "Few seconds ago", "3 minutes ago", "2 hours ago", "1 Sep", "24 Aug", "22 Aug", "21 Aug",
      "21 Aug", "20 Aug", "20 Aug",
  };

  private static String[] articleDummyTitles = {
      "Create recyclerview adapters like a boss", "On the road with Android",
      "Life can be tough - Here are few SDK improvements", "Google developer podcast - Android",
      "A maze of twisty little passages", "A stitch in time", "Android first week",
      "Just show me the code", "This is the droid you are looking for"
  };

  private static int[] colorList = {
      Color.parseColor("#ef9a9a"), Color.parseColor("#F48FB1"), Color.parseColor("#CE93D8"),
      Color.parseColor("#B39DDB"), Color.parseColor("#9FA8DA"), Color.parseColor("#90CAF9"),
      Color.parseColor("#81D4FA"), Color.parseColor("#C5E1A5"), Color.parseColor("#FFCC80")
  };

  private static int[] drawableList =
      { R.drawable.ic_circle, R.drawable.ic_heart, R.drawable.ic_star };

  public static List<Article> getArticles() {
    List<Article> articles = new ArrayList<>();
    int i = -1;
    for (String title : articleDummyTitles) {
      ++i;
      if(i==0)
      {
        continue;
      }
      Article article = new Article(i, title, "Android", articleDummyTimes[i], i == 0,
          i == 0 ? R.mipmap.ic_launcher : drawableList[random.nextInt(3)], colorList[i]);
      articles.add(article);
      break;
    }
    return articles;
  }

 /* public static Advertisement getAdvertisementOne() {
    return new Advertisement(1, "Do you like this library?",
        "Star and watch the library on Github to get latest updates",
        "https://github.com/DevAhamed/MultiViewAdapter");
  }

  public static Advertisement getAdvertisementTwo() {
    return new Advertisement(1, "Would you like to contribute?",
        "Fork the repo on Github and send the PR. Don't forget read the guidelines",
        "https://github.com/DevAhamed/MultiViewAdapter");
  }

  public static Advertisement getAdvertisementThree() {
    return new Advertisement(1, "Found an issue?",
        "Bug? Feature request? Need help? Create an issue on the issue tracker",
        "https://github.com/DevAhamed/MultiViewAdapter");
  }*/
}