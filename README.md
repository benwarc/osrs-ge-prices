# Old School RuneScape Grand Exchange Prices Batch

## Overview
The purpose of this Spring Batch application is to read from the [Old School RuneScape real-time pricing API](https://oldschool.runescape.wiki/w/RuneScape:Real-time_Prices)
and persist the results to a MongoDB database. To achieve this, the application defines two jobs; one to handle item
data, and the other to handle price data, which are further described below.

## Item Job
The item job reads the details of all items in Old School RuneScape via the [mapping endpoint](https://oldschool.runescape.wiki/w/RuneScape:Real-time_Prices#Mapping)
and writes them to the *items* collection. This job runs once every 24 hours to ensure item data remains current.

## Price Job
The price job reads the price details of all items in Old School RuneScape via the [5-minute prices endpoint](https://oldschool.runescape.wiki/w/RuneScape:Real-time_Prices#5-minute_prices)
and writes them to the *prices* collection. As the name implies, this job runs once every 5 minutes to get the
ever-changing price data within each interval.

The response of the 5-minute prices endpoint is formatted strangely in that it maps an item's price details to its item
ID as the key in one large map. Because of this, custom service logic is utilized to parse the response into a
structure that is easier for Spring Batch to handle.
