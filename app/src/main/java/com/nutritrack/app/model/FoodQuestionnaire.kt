package com.nutritrack.app.model

data class FoodQuestionnaire(
    val fruitsServings: Int,
    val vegetablesServings: Int,
    val wholeGrainsServings: Int,
    val processedFoodsServings: Int
) {
    fun calculateFruitScore(): Int {
        return when {
            fruitsServings >= 5 -> 100
            fruitsServings >= 3 -> 75
            fruitsServings >= 1 -> 50
            else -> 25
        }
    }
    
    fun calculateVegetableScore(): Int {
        return when {
            vegetablesServings >= 5 -> 100
            vegetablesServings >= 3 -> 75
            vegetablesServings >= 1 -> 50
            else -> 25
        }
    }
    
    fun calculateWholeGrainScore(): Int {
        return when {
            wholeGrainsServings >= 6 -> 100
            wholeGrainsServings >= 4 -> 75
            wholeGrainsServings >= 2 -> 50
            else -> 25
        }
    }
    
    fun calculateProcessedFoodScore(): Int {
        return when {
            processedFoodsServings == 0 -> 100
            processedFoodsServings <= 2 -> 75
            processedFoodsServings <= 4 -> 50
            else -> 25
        }
    }
    
    fun calculateOverallScore(): Int {
        val fruitScore = calculateFruitScore()
        val vegetableScore = calculateVegetableScore()
        val wholeGrainScore = calculateWholeGrainScore()
        val processedFoodScore = calculateProcessedFoodScore()
        
        return (fruitScore * 0.25 + vegetableScore * 0.3 + 
                wholeGrainScore * 0.25 + processedFoodScore * 0.2).toInt()
    }
    
    fun determinePersona(): String {
        val score = calculateOverallScore()
        return when {
            score >= 80 -> "Nutrition Conscious"
            score >= 70 -> "Health Enthusiast"
            score >= 60 -> "Balance Seeker"
            score >= 50 -> "Moderate Eater"
            else -> "Convenience Diner"
        }
    }
} 