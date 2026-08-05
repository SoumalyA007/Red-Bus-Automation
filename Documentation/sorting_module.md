# Module 8 - Sorting Implementation Guide

## Overview
This document describes the complete implementation of the Sorting Module for the Red Bus Automation project. The module includes functionality to sort bus results by various criteria including price, departure time, duration, and rating.

## New Files Created

### 1. **SortType.java** (Enum)
**Location:** `src/test/java/enums/SortType.java`

**Purpose:** Defines all available sorting options in the application.

**Sorting Options:**
- `LOWEST_PRICE` - Sort results by price in ascending order
- `HIGHEST_PRICE` - Sort results by price in descending order
- `EARLIEST_DEPARTURE` - Sort results by departure time (ascending)
- `LATEST_DEPARTURE` - Sort results by departure time (descending)
- `SHORTEST_DURATION` - Sort results by journey duration (ascending)
- `HIGHEST_RATING` - Sort results by bus rating (descending)

**Usage:**
```java
SortType.LOWEST_PRICE.getSortOption()  // Returns "Lowest Price"
```

---

### 2. **SortingPage.java** (Page Object)
**Location:** `src/test/java/pageObjects/SortingPage.java`

**Purpose:** Page Object that encapsulates all sorting interactions and verifications.

**Key Methods:**

#### Interaction Methods:
- `clickSortDropdown()` - Opens/closes the sort dropdown menu
- `selectSortOption(SortType sortType)` - Selects a specific sort option and waits for results to reload
- `waitForSortDropdownDisplayed()` - Waits for sort dropdown to be visible
- `waitForSortOptionsDisplayed()` - Waits for sort options to be visible
- `waitForResultsToReload()` - Waits for bus cards to reload after sorting

#### Verification Methods:
- `isSortDropdownDisplayed()` - Verifies if sort dropdown is displayed
- `isSortOptionAvailable(SortType sortType)` - Checks if a specific sort option is available
- `getCurrentSortSelection()` - Returns the currently selected sort option

**Locators Used:**
```xpath
// Sort dropdown/button
//div[contains(@class,'sortDropdown')] | //div[@role='button' and contains(text(), 'Sort')]

// Sort options
//div[@role='option'] | //li[contains(@class,'sort')]

// Sort buttons
//div[contains(@class,'sortOption___')] | //button[contains(@class,'sort')]

// Bus cards (for reload detection)
//li[contains(@class,'tupleWrapper')]
```

---

### 3. **SortingTest.java** (Test Cases)
**Location:** `src/test/java/testCases/SortingTest.java`

**Purpose:** Comprehensive test cases for all sorting functionality.

**Test Cases:**

| Test ID | Test Name | Description |
|---------|-----------|-------------|
| TC_001 | sort_by_lowest_price | Verifies sorting by lowest price works correctly |
| TC_002 | sort_by_highest_price | Verifies sorting by highest price works correctly |
| TC_003 | sort_by_earliest_departure | Verifies sorting by earliest departure time works correctly |
| TC_004 | sort_by_latest_departure | Verifies sorting by latest departure time works correctly |
| TC_005 | sort_by_shortest_duration | Verifies sorting by shortest duration works correctly |
| TC_006 | sort_by_highest_rating | Verifies sorting by highest rating works correctly |
| TC_007 | sort_dropdown_availability | Verifies all sort options are available in the dropdown |
| TC_008 | sort_switch_lowest_to_highest_price | Verifies switching between sort options works |
| TC_009 | sort_persistence_across_actions | Verifies sort order is maintained |

---

## Enhanced Files

### 1. **SearchPage.java** (Extended with Sorting Verification)
**Location:** `src/test/java/pageObjects/SearchPage.java`

**New Verification Methods Added:**

#### Sorting Verification:
- `areCardsSortedByLowestPrice()` - Verifies ascending price order
- `areCardsSortedByHighestPrice()` - Verifies descending price order
- `areCardsSortedByEarliestDeparture()` - Verifies ascending departure time
- `areCardsSortedByLatestDeparture()` - Verifies descending departure time
- `areCardsSortedByShortestDuration()` - Verifies ascending duration
- `areCardsSortedByHighestRating()` - Verifies descending rating order

#### Helper Methods:
- `compareTime(String time1, String time2)` - Compares two time strings (HH:MM format)
- `compareDuration(String duration1, String duration2)` - Compares two duration strings
- `parseDurationToMinutes(String duration)` - Converts duration string to minutes

**How They Work:**
Each verification method iterates through all bus cards and validates that they are in the correct sorted order. For example:
```java
public boolean areCardsSortedByLowestPrice() {
    List<BusCard> cards = getAllBusCardDetails();
    for (int i = 0; i < cards.size() - 1; i++) {
        if (cards.get(i).getPrice() > cards.get(i + 1).getPrice()) {
            return false;
        }
    }
    return true;
}
```

---

### 2. **BaseClass.java** (Extended for Sorting Support)
**Location:** `src/test/java/testBase/BaseClass.java`

**Changes:**
- Added `SortingPage sortingPage` field for dependency injection
- Initialized `sortingPage` in the `setup()` method with: `sortingPage = new SortingPage(driver);`
- This allows all test classes extending BaseClass to access sorting functionality

---

## Architecture & Design Patterns

### 1. **Page Object Model (POM)**
- `SortingPage` follows the POM pattern used throughout the project
- Encapsulates all UI interactions and locators
- Easy to maintain if UI changes

### 2. **Enum Pattern**
- `SortType` enum provides type-safe sorting options
- Prevents typos and invalid sort values
- Self-documenting code

### 3. **Separation of Concerns**
- **SortingPage**: Handles UI interactions
- **SearchPage**: Handles verification of results
- **SortingTest**: Contains test logic

### 4. **Composition & Reusability**
- Tests inherit from BaseClass which initializes all page objects
- Sorting functionality integrates seamlessly with existing filtering functionality

---

## Usage Example

```java
// In a test method
public void testSorting() {
    // Search for buses
    helper.searchBuses("Kolkata", "Burdwan", LocalDate.now().plusDays(10));
    Assert.assertTrue(sp.waitForBusCardsToLoad(), "Cards did not load");

    // Apply lowest price sort
    sortingPage.selectSortOption(SortType.LOWEST_PRICE);

    // Verify results are sorted
    Assert.assertTrue(sp.areCardsSortedByLowestPrice(),
            "Cards are not sorted by lowest price");
}
```

---

## Integration with Existing Flow

The sorting module integrates seamlessly with the existing project:

### Before Sorting:
1. User searches for buses (existing flow)
2. Results are displayed

### After Sorting:
1. User searches for buses (existing flow)
2. Results are displayed
3. **User can apply sorting** (NEW)
4. Results are re-ordered
5. **Verification methods can confirm sorting is applied** (NEW)

### Can be Combined with Filters:
```java
// Filter by AC buses AND sort by lowest price
searchresultsfilterpage.selectFilterOption(FilterHeaders.BUS_TYPE, FilterChoice.AC);
sortingPage.selectSortOption(SortType.LOWEST_PRICE);
Assert.assertTrue(sp.allCardsMatchBusType("AC"));
Assert.assertTrue(sp.areCardsSortedByLowestPrice());
```

---

## Duration & Time Format Handling

The verification methods handle multiple time and duration formats:

### Time Formats Supported:
- `HH:MM` (e.g., "14:30")
- `H:MM` (e.g., "9:45")

### Duration Formats Supported:
- `HhMm` (e.g., "2h 30m")
- `Hh` (e.g., "5h")
- `Mm` (e.g., "45m")
- `H:MM` (e.g., "2:30")

Example:
```java
parseDurationToMinutes("2h 30m")  // Returns 150 minutes
parseDurationToMinutes("45m")     // Returns 45 minutes
parseDurationToMinutes("1:30")    // Returns 90 minutes
```

---

## Running the Tests

### Run All Sorting Tests:
```bash
mvn test -Dtest=SortingTest
```

### Run Specific Test:
```bash
mvn test -Dtest=SortingTest#TC_001_sort_by_lowest_price
```

### Run via TestNG (if configured):
```bash
# Right-click on SortingTest.java and select "Run Tests"
```

---

## Error Handling

The implementation includes robust error handling:

1. **Null Checks**: Methods validate element existence before interaction
2. **Timeout Handling**: Uses WebDriverWait with appropriate timeouts
3. **Stale Element Recovery**: Waits for element staleness and re-fetches
4. **Exception Handling**: Graceful handling of parsing errors in time/duration comparisons

---

## Future Enhancements

Potential improvements for the sorting module:

1. **Multi-Column Sorting**: Sort by multiple criteria simultaneously
2. **Custom Sorting**: Allow users to define custom sort logic
3. **Sort Persistence**: Save user's sort preference
4. **Performance Optimization**: Cache sorted results
5. **Advanced Filtering**: Combine sorting with price range filters

---

## File Summary

| File | Type | Purpose |
|------|------|---------|
| SortType.java | Enum | Defines sorting options |
| SortingPage.java | Page Object | Handles sorting UI interactions |
| SortingTest.java | Test Cases | Comprehensive sorting tests |
| SearchPage.java | Enhanced | Added sorting verification methods |
| BaseClass.java | Enhanced | Added SortingPage initialization |

---

## Compatibility

- **Java Version**: 21 (as per pom.xml)
- **Selenium Version**: 4.x (standard with project)
- **TestNG**: Compatible with existing test framework
- **Browser Support**: All browsers supported by the project (Chrome, Firefox, Edge)

---

## Troubleshooting

### Issue: Sort dropdown not found
**Solution**: Update XPath locators in SortingPage if RedBus UI changes

### Issue: Sorting verification fails
**Solution**: Check bus card data extraction in SearchPage.extractCardDetails()

### Issue: Time/Duration parsing errors
**Solution**: Update format handling in parseTime() and parseDurationToMinutes()

---

## Contact & Support

For questions or issues with the sorting module, refer to:
- Test logs in `logs/` directory
- Screenshots in `target/screenshots/` directory
- TestNG report in `target/surefire-reports/`

---

**Module 8 - Sorting Implementation Complete ✓**
