/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 *  v. 2.0. If a copy of the MPL was not distributed with this file,
 *  You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package me.jeffshaw.mima

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GroupNameVersionTest extends AnyFlatSpec with Matchers {
  "serdes" should "go back and forth from a gradle dependency string" in {
    val dependency = "group:name:version"
    val gnv = GroupNameVersion.fromString(dependency)
    gnv.get().group shouldBe "group"
    gnv.get().name shouldBe "name"
    gnv.get().version shouldBe "version"
    gnv.get().asString() shouldBe dependency
  }
}
