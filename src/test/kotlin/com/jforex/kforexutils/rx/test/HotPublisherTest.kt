package com.jforex.kforexutils.rx.test

import com.jforex.kforexutils.rx.HotPublisher
import io.kotlintest.specs.FreeSpec
import io.reactivex.observers.TestObserver

class HotPublisherTest : FreeSpec()
{
    private val hotPublisher = HotPublisher<Int>()

    override fun isInstancePerTest() = true

    private fun subscribe(): TestObserver<Int> = hotPublisher
        .observable()
        .test()

    init
    {
        val firstItem = 1
        hotPublisher.onNext(firstItem)
        "No item is observed when subscribed after item has been published" {
            subscribe().assertNoValues()
        }

        "Given subscription and first published item" - {
            val testObserver = subscribe()
            hotPublisher.onNext(firstItem)

            "First item is observed" - {
                val secondItem = 2
                testObserver.assertValue(firstItem)

                "Second Item is observed" {
                    hotPublisher.onNext(secondItem)
                    testObserver.assertValues(firstItem, secondItem)
                }

                "Given unsubscribed, second item is not observed" {
                    hotPublisher.unsubscribe()
                    hotPublisher.onNext(secondItem)
                    testObserver.assertValues(firstItem)
                }
            }
        }
    }
}