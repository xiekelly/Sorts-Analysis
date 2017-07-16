//----------------------------------------------------------------------------
//Sorts.java               by Dale/Joyce/Weems                     Chapter 11
//
//Test harness used to run sorting algorithms.
//----------------------------------------------------------------------------

import java.util.*;
import java.text.DecimalFormat;

/**
 * Includes algorithms for all 6 types of sorts
 * and main method.
 * 
 * @author Dale/Joyce/Weems, Edited by Kelly Xie
 */
public class Kelly_Xie_Sorts {
	static int SIZE; // size of array to be sorted
	static int[] values; // values to be sorted

	static void initValues()
	// Initializes the values array with random integers from 0 to 99.
	{
		Random rand = new Random();
		for (int index = 0; index < SIZE; index++)
			values[index] = Math.abs(rand.nextInt()) % SIZE*3;
	}

	static public boolean isSorted()
	// Returns true if the array values are sorted and false otherwise.
	{
		for (int index = 0; index < (SIZE - 1); index++)
			if (values[index] > values[index + 1])
				return false;
		return true;
	}

	static public void swap(int index1, int index2)
	// Precondition: index1 and index2 are >= 0 and < SIZE.
	//
	// Swaps the integers at locations index1 and index2 of the values array.
	{
		int temp = values[index1];
		values[index1] = values[index2];
		values[index2] = temp;
	}

	static public void printValues()
	// Prints all the values integers.
	{
		int value;
		DecimalFormat fmt = new DecimalFormat("00");
		System.out.println("The values array is:");
		for (int index = 0; index < SIZE; index++) {
			value = values[index];
			if (((index + 1) % 10) == 0)
				System.out.println(fmt.format(value));
			else
				System.out.print(fmt.format(value) + " ");
		}
		System.out.println();
	}

	/////////////////////////////////////////////////////////////////
	//
	// Selection Sort
	
	static int[] minIndex(int startIndex, int endIndex)
	// Returns the index of the smallest value in
	// values[startIndex]..values[endIndex].
	{
		int indexOfMin = startIndex;
		int comparisons = 0;
		for (int index = startIndex + 1; index <= endIndex; index++) {
			comparisons++; // count number of comparisons each time minIndex is called
			if (values[index] < values[indexOfMin])
				indexOfMin = index;
		}
		return new int[] {indexOfMin, comparisons};
	}

	static long selectionSort()
	// Sorts the values array using the selection sort algorithm.
	{
		long comparisonsSelection = 0;
		long swapsSelection = 0;
		int endIndex = SIZE - 1;
		for (int current = 0; current < endIndex; current++) {
			int result[] = minIndex(current, endIndex);
			// result[0] = indexOfMin
			// result[1] = comparisons
			comparisonsSelection += result[1]; // keep track of total comparisons
			swap(current, result[0]);
			swapsSelection++; // keep track of total swaps
		}
		System.out.println("   swaps: " + swapsSelection);
		return comparisonsSelection;
	}

	/////////////////////////////////////////////////////////////////
	//
	// Bubble Sort
	
	static int[] bubbleUp(int startIndex, int endIndex)
	// Switches adjacent pairs that are out of order
	// between values[startIndex]..values[endIndex]
	// beginning at values[endIndex].
	{
		int comparisons = 0;
		int swaps = 0;
		for (int index = endIndex; index > startIndex; index--) {
			comparisons++; // count number of comparisons each time bubbleUp is called
			if (values[index] < values[index - 1]) {
				swap(index, index - 1);
				swaps++;
			}
		}
		return new int[] {comparisons, swaps};
	}

	static long bubbleSort()
	// Sorts the values array using the bubble sort algorithm.
	{
		long comparisonsBubble = 0;
		long swapsBubble = 0;
		int current = 0;
		int[] result;
		while (current < (SIZE - 1)) {
			result = bubbleUp(current, SIZE - 1);
			comparisonsBubble += result[0]; // keep track of total comparisons
			swapsBubble += result[1]; // keep track of total swaps
			current++;
		}
		System.out.println("   swaps: " + swapsBubble);
		return comparisonsBubble;
	}

	/////////////////////////////////////////////////////////////////
	//
	// Short Bubble Sort

	static Result bubbleUp2(int startIndex, int endIndex)
	// Switches adjacent pairs that are out of order
	// between values[startIndex]..values[endIndex]
	// beginning at values[endIndex].
	//
	// Returns false if a swap was made; otherwise, returns true.
	{
		int comparisons = 0;
		int swaps = 0;
		boolean sorted = true;
		for (int index = endIndex; index > startIndex; index--) {
			comparisons++; // increment comparisons if array is unsorted
			if (values[index] < values[index - 1]) {
				swap(index, index - 1);
				swaps++;
				sorted = false;
			}
		}
		Result result = new Result(sorted, comparisons, swaps);
		return result;
	}
	
	// Class for storing sorted boolean value and 
	// number of comparisons from bubbleUp2 method
	static class Result {
		  private boolean sorted;
		  private int comparisons;
		  private int swaps;

		  Result(boolean sorted, int comparisons, int swaps) {
		    this.sorted = sorted;
		    this.comparisons = comparisons;
		    this.swaps = swaps;
		  }
	}

	static long shortBubble()
	// Sorts the values array using the bubble sort algorithm.
	// The process stops as soon as values is sorted.
	{
		long comparisonsShortBubble = 0;
		long swapsShortBubble = 0;
		int current = 0;
		boolean sorted = false;
		Result result;
		while ((current < (SIZE - 1)) && !sorted) {
			result = bubbleUp2(current, SIZE - 1);
			sorted = result.sorted;
			comparisonsShortBubble += result.comparisons; // keep track of total comparisons
			swapsShortBubble += result.swaps; // keep track of total swaps
			current++;
		}
		System.out.println("   swaps: " + swapsShortBubble);
		return comparisonsShortBubble;
	}

	/////////////////////////////////////////////////////////////////
	//
	// Insertion Sort

	static int[] insertItem(int startIndex, int endIndex)
	// Upon completion, values[0]..values[endIndex] are sorted.
	{
		int comparisons = 0;
		int swaps = 0;
		boolean finished = false;
		int current = endIndex;
		boolean moreToSearch = true;
		while (moreToSearch && !finished) {
			comparisons++; // increment count of comparisons
			if (values[current] < values[current - 1]) {
				swap(current, current - 1);
				swaps++; // increment count of swaps
				current--;
				moreToSearch = (current != startIndex);
				
			} else
				finished = true;
		}
		return new int[] {comparisons, swaps};
	}

	static long insertionSort()
	// Sorts the values array using the insertion sort algorithm.
	{
		long comparisonsInsertion = 0;
		long swapsInsertion = 0;
		int[] result;
		for (int count = 1; count < SIZE; count++) {
			result = insertItem(0, count);
			comparisonsInsertion += result[0]; // keep track of total comparisons
			swapsInsertion += result[1]; // keep track of total swaps
		}
		System.out.println("   swaps: " + swapsInsertion);
		return comparisonsInsertion;
	}

	/////////////////////////////////////////////////////////////////
	//
	// Merge Sort

	/**
	 * Merging two subarrays.
	 * @param leftFirst, leftLast, rightFirst, rightLast indexes of subarrays
	 * @return int number of comparisons
	 * @author More efficient implmentation by Joanna Klukowska.
	 */
	static long[] merge(int leftFirst, int leftLast, int rightFirst, int rightLast)
	// Preconditions: values[leftFirst]..values[leftLast] are sorted.
	// values[rightFirst]..values[rightLast] are sorted.
	//
	// Sorts values[leftFirst]..values[rightLast] by merging the two subarrays.
	{
		int comparisons = 0;
		int swaps = 0;
		int currentSize = rightLast - leftFirst + 1; 
		int[] tempArray = new int[currentSize];
		int index = 0; //leftFirst;
		int saveFirst = leftFirst; // to remember where to copy back

		while ((leftFirst <= leftLast) && (rightFirst <= rightLast)) {
			if (values[leftFirst] < values[rightFirst]) {
				tempArray[index] = values[leftFirst];
				leftFirst++;
			} else {
				tempArray[index] = values[rightFirst];
				rightFirst++;
			}
			comparisons++; // increment count of comparisons from merging
			swaps++; // increment count of swaps
			index++;
		}

		while (leftFirst <= leftLast)
		// Copy remaining items from left half.
		{
			tempArray[index] = values[leftFirst];
			leftFirst++;
			index++;
			swaps++; // increment count of swaps
		}

		while (rightFirst <= rightLast)
		// Copy remaining items from right half.
		{
			tempArray[index] = values[rightFirst];
			rightFirst++;
			index++;
			swaps++; // increment count of swaps
		}

		for (index = saveFirst; index <= rightLast; index++) {
			values[index] = tempArray[index-saveFirst]; // copy temp to original array
			swaps++; // increment count of swaps
		}
		
		return new long[] {comparisons, swaps};
	}

	/**
	 * Splitting array into subarrays.
	 * @param first, last indexes of array
	 * @return long total number of comparisons for the split
	 */
	static long[] mergeSort(int first, int last)
	// Sorts the values array using the merge sort algorithm.
	{
		long comparisonsMerge = 0;
		long swapsMerge = 0;
		long[] result;
		long[] result2;
		if (first < last) {
			int middle = (first + last) / 2;
			
			result = mergeSort(first, middle);
			comparisonsMerge += result[0]; // comparisons from sorting first half subarray
			swapsMerge += result[1];
			result = mergeSort(middle + 1, last);
			comparisonsMerge += result[0]; // comparisons from sorting last half subarray
			swapsMerge += result[1];
			
			// comparisons and swaps from merging the two subarrays
			result2 = merge(first, middle, middle + 1, last);
			comparisonsMerge += result2[0];
			swapsMerge += result2[1];
		}
		return new long[] {comparisonsMerge, swapsMerge};
	}

	/////////////////////////////////////////////////////////////////
	//
	// Quick Sort

	static int[] split(int first, int last) {
		int comparisons = 0;
		int swaps = 0;
		int splitVal = values[first];
		int saveF = first;
		boolean onCorrectSide;

		first++;
		do {
			onCorrectSide = true;
			while (onCorrectSide) { // move first toward last
				comparisons++; // increment count for every level of split
				if (values[first] > splitVal)
					onCorrectSide = false;
				else {
					first++;
					onCorrectSide = (first <= last);
				}
			}
			onCorrectSide = (first <= last);
			while (onCorrectSide) { // move last toward first
				comparisons++; // increment count for every level of split
				if (values[last] <= splitVal)
					onCorrectSide = false;
				else {
					last--;
					onCorrectSide = (first <= last);
				}
			}
			if (first < last) {
				swap(first, last);
				swaps++;
				first++;
				last--;
			}
		} while (first <= last);

		swap(saveF, last);
		swaps++;
		return new int[] {last, comparisons, swaps};
	}

	static long[] quickSort(int first, int last) {
		long comparisonsQuick = 0;
		long swapsQuick = 0;
		if (first < last) {
			int[] result;
			int splitPoint;
			
			result = split(first, last);
			splitPoint = result[0];
			comparisonsQuick = result[1]; // keep track of total comparisons
			swapsQuick = result[2]; // keep track of total swaps
			// values[first]..values[splitPoint - 1] <= splitVal
			// values[splitPoint] = splitVal
			// values[splitPoint+1]..values[last] > splitVal

			comparisonsQuick += quickSort(first, splitPoint - 1)[0];
			comparisonsQuick += quickSort(splitPoint + 1, last)[0];
		}
		return new long[] {comparisonsQuick, swapsQuick};
	}

	/////////////////////////////////////////////////////////////////
	//
	// Heap Sort
	
	static long comparisons = 0;

	static int newHole(int hole, int lastIndex, int item)
	// If either child of hole is larger than item this returns the index
	// of the larger child; otherwise it returns the index of hole.
	{
		comparisons = 0;
		int left = (hole * 2) + 1;
		int right = (hole * 2) + 2;
		if (left > lastIndex)
			// hole has no children
			return hole;
		else if (left == lastIndex) {
			// hole has left child only
			comparisons++;
			if (item < values[left]) 
				// item < left child
				return left;
			else
				// item >= left child
				return hole;
		}
		else {
			// hole has two children
			comparisons++;
			if (values[left] < values[right]) {
				// left child < right child
				comparisons++;
				if (values[right] <= item)
					// right child <= item
					return hole;
				else
					// item < right child
					return right;
			}
			else {
				// left child >= right child
				comparisons++;
				if (values[left] <= item)
					// left child <= item
					return hole;
				else
					// item < left child
					return left;
			}
		}
	}

	static long[] reheapDown(int item, int root, int lastIndex)
	// Precondition: Current root position is "empty".
	//
	// Inserts item into the tree and ensures shape and order properties.
	{
		int hole = root; // current index of hole
		int newhole; // index where hole should move to
		long comparisonsTotal = 0;
		long swaps = 0;
		
		newhole = newHole(hole, lastIndex, item); // find next hole
		comparisonsTotal += comparisons;
		while (newhole != hole) {
			values[hole] = values[newhole]; // move value up
			swaps++;
			hole = newhole; // move hole down
			newhole = newHole(hole, lastIndex, item); // find next hole
			comparisonsTotal += comparisons;
		}
		values[hole] = item; // fill in the final hole
		return new long[] {comparisonsTotal, swaps};
	}

	static long heapSort()
	// Sorts the values array using the heap sort algorithm.
	{
		long comparisonsHeap = 0;
		long swapsHeap = 0;
		long[] result;
		int index;
		// Convert the array of values into a heap.
		for (index = SIZE / 2 - 1; index >= 0; index--) {
			result = reheapDown(values[index], index, SIZE - 1);
			comparisonsHeap += result[0];
			swapsHeap += result[1];
		}

		// Sort the array.
		for (index = SIZE - 1; index >= 1; index--) {
			swap(0, index);
			swapsHeap++;
			result = reheapDown(values[0], 0, index - 1);
			comparisonsHeap += result[0];
			swapsHeap += result[1];
		}
		System.out.println("   swaps: "+ swapsHeap);
		return comparisonsHeap;
	}

	/////////////////////////////////////////////////////////////////
	//
	// Main

	public static void main(String[] args) {
		
		// O(N^2) performance sorts
		for (int i=10000; i<=100000; i+=10000) { // loop for automation
			
			SIZE = i;
			values = new int[SIZE];
			initValues();
			//printValues();
			
			System.out.println("--------------------------------------");
			System.out.println("N = " + SIZE);
			System.out.println();
			
			System.out.println("values is sorted: " + isSorted());
			System.out.println();
			
			// make calls to sorting method here
			
			System.out.println("Selection sort: ");
			System.out.println("   comparisons: " + selectionSort() );
			
			//System.out.println("Bubble sort: ");
			//System.out.println("   comparisons: " + bubbleSort() );
			
			//System.out.println("Short bubble sort: ");
			//System.out.println("   comparisons: " + shortBubble() );
			
			//System.out.println("Insertion sort: ");
			//System.out.println("   comparisons: " + insertionSort() );
			
			//System.out.println("Merge sort: ");
			//long[] tempMerge = mergeSort(0, SIZE - 1);
			//System.out.println("   swaps: " + tempMerge[1]);
			//System.out.println("   comparisons: " + tempMerge[0]);
			
			//System.out.println("Quick sort: ");
			//long[] tempQuick = quickSort(0, SIZE - 1);
			//System.out.println("   swaps: " + tempQuick[1] );
			//System.out.println("   comparisons: " + tempQuick[0] );
			
			//System.out.println("Heap sort: ");
			//System.out.println("   comparisons: " + heapSort() );
			
			System.out.println();
	
			
			//printValues();
			System.out.println("values is sorted: " + isSorted());
			System.out.println();
		
		}
		
		// O(N log N) performance sorts
		for (int i=100000; i<=1000000; i+=100000) {
		
			SIZE = i;
			values = new int[SIZE];
			initValues();
			//printValues();
			
			System.out.println("--------------------------------------");
			System.out.println("N = " + SIZE);
			System.out.println();
			
			System.out.println("values is sorted: " + isSorted());
			System.out.println();
		
			// make calls to sorting method here
			
			//System.out.println("Merge sort: ");
			//long[] tempMerge2 = mergeSort(0, SIZE - 1);
			//System.out.println("   swaps: " + tempMerge2[1]);
			//System.out.println("   comparisons: " + tempMerge2[0]);
			
			//System.out.println("Quick sort: ");
			//long[] tempQuick2 = quickSort(0, SIZE - 1);
			//System.out.println("   swaps: " + tempQuick2[1] );
			//System.out.println("   comparisons: " + tempQuick2[0] );
			
			//System.out.println("Heap sort: ");
			//System.out.println("   comparisons: " + heapSort() );
		
			System.out.println();
	
			//printValues();
			System.out.println("values is sorted: " + isSorted());
			System.out.println();
		}
	}
}
