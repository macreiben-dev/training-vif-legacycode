package com.gildedrose;

class GildedRose {
    private final int MAX_QUALITY = 50;
    private final int MIN_QUALITY = 0;
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {

            Item currentItem = items[i];

            if (currentItem.name.equals("Aged Brie")
                || currentItem.name.equals("Backstage passes to a TAFKAL80ETC concert")) {

                currentItem.quality = incrementQuality(currentItem);

                if (currentItem.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    computeBackstagePassQuality(currentItem);
                }

            } else {
                if (!currentItem.name.equals("Sulfuras, Hand of Ragnaros")) {
                    currentItem.quality = decreaseQuality(currentItem);
                }
            }

            if (!currentItem.name.equals("Sulfuras, Hand of Ragnaros")) {
                currentItem.sellIn = currentItem.sellIn - 1;
            }

            if (currentItem.sellIn < 0) {
                if (!currentItem.name.equals("Aged Brie")) {
                    if (!currentItem.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (canDecreaseQuality(currentItem)) {
                            if (!currentItem.name.equals("Sulfuras, Hand of Ragnaros")) {
                                currentItem.quality = decreaseQuality(currentItem);
                            }
                        }
                    } else {
                        currentItem.quality = currentItem.quality - currentItem.quality;
                    }
                } else {

                    if (canIncreaseQuality(currentItem)) {
                        currentItem.quality = incrementQuality(currentItem);
                    }
                }
            }
        }
    }

    private boolean canDecreaseQuality(Item currentItem) {
        return currentItem.quality > 0;
    }

    private int decreaseQuality(Item currentItem) {
        if (canDecreaseQuality(currentItem))
            return currentItem.quality - 1;

        return MIN_QUALITY;
    }

    private void computeBackstagePassQuality(Item currentItem) {
        if (currentItem.sellIn < 11) {
            currentItem.quality = incrementQuality(currentItem);
        }

        if (currentItem.sellIn < 6) {
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
