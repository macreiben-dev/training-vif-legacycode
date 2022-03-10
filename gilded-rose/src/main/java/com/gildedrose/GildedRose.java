package com.gildedrose;

class GildedRose {
    private final int MAX_QUALITY = 50;
    private final int MIN_QUALITY = 0;
    private final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private final String SULFURAS_HAND_OF_RAGNAROS = "Sulfuras, Hand of Ragnaros";
    private final String AGED_BRIE = "Aged Brie";
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {

            Item currentItem = items[i];

            // ========================

            if (currentItem.name.equals(AGED_BRIE)
                || currentItem.name.equals(BACKSTAGE_PASS)) {

                currentItem.quality = incrementQuality(currentItem);

                if (currentItem.name.equals(BACKSTAGE_PASS)) {
                    computeBackstagePassQuality(currentItem);
                }

            } else {
                if (!currentItem.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                    currentItem.quality = decreaseQuality(currentItem);
                }
            }

            // ========================

            if (!currentItem.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                currentItem.sellIn = currentItem.sellIn - 1;
            }

            if (currentItem.sellIn < 0) {
                if (currentItem.name.equals(AGED_BRIE)) {
                    currentItem.quality = incrementQuality(currentItem);
                } else {
                    if (currentItem.name.equals(BACKSTAGE_PASS)) {
                        currentItem.quality = currentItem.quality - currentItem.quality;
                    } else {
                        if (canDecreaseQuality(currentItem)) {
                            if (!currentItem.name.equals(SULFURAS_HAND_OF_RAGNAROS)) {
                                currentItem.quality = decreaseQuality(currentItem);
                            }
                        }
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
