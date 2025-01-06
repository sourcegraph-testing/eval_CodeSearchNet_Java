/*
 *
 * Magnetic Track Parser
 * https://github.com/sualeh/magnetictrackparser
 * Copyright (c) 2014-2016, Sualeh Fatehi.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */
package us.fatehi.magnetictrack.bankcard;


import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.Serializable;
import java.util.regex.Matcher;

import us.fatehi.creditcardnumber.BaseRawData;
import us.fatehi.creditcardnumber.DisposableStringData;
import us.fatehi.creditcardnumber.RawData;

abstract class BaseTrackData
  extends BaseRawData
  implements RawData, Serializable
{

  private static final long serialVersionUID = 7821463290736676016L;

  protected static String getGroup(final Matcher matcher, final int group)
  {
    final int groupCount = matcher.groupCount();
    if (groupCount > group - 1)
    {
      return matcher.group(group);
    }
    else
    {
      return null;
    }
  }

  private final DisposableStringData discretionaryData;

  BaseTrackData(final String rawTrackData, final String discretionaryData)
  {
    super(rawTrackData);
    this.discretionaryData = new DisposableStringData(discretionaryData);
  }

  /**
   * @see {@link #disposeDiscretionaryData}
   */
  @Deprecated
  public void clearDiscretionaryData()
  {
    disposeDiscretionaryData();
  }

  /**
   * Disposes discretionary data from memory. Following recommendations
   * from the <a href=
   * "http://docs.oracle.com/javase/6/docs/technotes/guides/security/crypto/CryptoSpec.html#PBEEx">Java
   * Cryptography Architecture (JCA) Reference Guide</a>
   */
  public void disposeDiscretionaryData()
  {
    discretionaryData.disposeData();
  }

  /**
   * Gets discretionary data on the track.
   *
   * @return Discretionary data.
   */
  public String getDiscretionaryData()
  {
    return discretionaryData.getData();
  }

  /**
   * Whether discretionary data is present.
   *
   * @return True if discretionary data is available
   */
  public boolean hasDiscretionaryData()
  {
    return discretionaryData.hasData() && !isBlank(discretionaryData);
  }

  @Override
  public String toString()
  {
    return getRawData();
  }

}
