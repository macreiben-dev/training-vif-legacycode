package com.gildedrose;

class GildedRose {
    private final int MAX_QUALITY = 50;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item currentItem = items[i];

            if (!items[i].name.equals("Aged Brie")
                && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {

                if (canIncreaseQuality(currentItem)) {
                    currentItem.quality = incrementQuality(currentItem);

                    if (currentItem.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        computeBackstagePassQuality(currentItem);
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {

                    if (canIncreaseQuality(currentItem)) {
                        currentItem.quality = incrementQuality(currentItem);
                    }
                }
            }
        }
    }

    private void computeBackstagePassQuality(Item currentItem) {
        if (currentItem.sellIn < 11 && canIncreaseQuality(currentItem)) {
            currentItem.quality = incrementQuality(currentItem);
        }

        if (currentItem.sellIn < 6 && canIncreaseQuality(currentItem)) {
            currentItem.quality = incrementQuality(currentItem);
        }
    }

    private boolean canIncreaseQuality(Item currentItem) {
        return currentItem.quality < MAX_QUALITY;
    }

    private int incrementQuality(Item currentItem) {
        if (canIncreaseQuality(currentItem))
            return currentItem.quality + 1;

        return MAX_QUALITY;
    }
}
