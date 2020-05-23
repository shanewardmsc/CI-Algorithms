// Initial Random Sample of Population
for (int j = 0; j < Pop_g; j++)
{
  double[] temp = generateRandomSolution(bounds, probDim);
  for (int n = 0; n < probDim; n++)
    Pop_g[j][n] = temp[n];
    fitnesses[j] = problem.f(Pop_g[j]);
    if (j == 0 || fitnesses[j] < fBest)
    {
      fBest = fitnesses[j];
      for (int n = 0; n < probDim; n++)
        best[n] = Pop_g[j][n];
    }
    i++;
}

// Temporary variable
double[] CurrentParticle = new double[probDim];
double[] NewParticle = new double[probDim];
double[] CrossoverParticle = new double[probDim];
double CurrentFitness = Double.NaN;
double CrossoverFitness = Double.NaN;
// While I < maximum number of evaluations
while (i < maxEvaluations)
{
  double[][] NewPopulation = new double[Pop_g][probDim];
  for (int j = 0; j < Pop_g && i < maxEvaluations; j++)
  {
    for (int n = 0; n < probDim; n++)
      CurrentParticle[n] = Pop_g[j][n];
      CurrentFitness = fitnesses[j];
      // Mutation Strategy //
      double x[] = {1};
      double y[] = {1};
      int n = x.length;
      double[] x_off = new double[n];
      int index = RandUtils.randomInteger(n-1);
      for (int o = 0; o < n; o++)
      {
        if (RandUtils.random() < CR || i == index)
          x_off[i] = y[i];
        else
          x_off[i] = x[i];
      }
      // Crossover strategy //
      for (int k = 0; k < n; k++)
      {
        if (RandUtils.random() < CR || i == index)
          x_off[k] = y[k];
        else
          x_off[k] = x[k];
      }
      CrossoverParticle = toro(CrossoverParticle, bounds);
      CrossoverFitness = problem.f(CrossoverParticle);
      i++;
      // Replacement of value
      if (CrossoverFitness < CurrentFitness)
      {
        for (int l = 0; l < probDim; n++)
          NewPopulation[l][n] = CrossoverParticle[n];
          fitnesses[j] = CrossoverFitness;
          // Update the best value
          if (CrossoverFitness < fBest)
          {
            fBest = CrossoverFitness;
            for (int k = 0; k < probDim; k++)
              best[k] = CrossoverParticle[k];
          }
      }
      else
      {
        for (int m = 0; m < probDim; m++)
          NewPopulation[m][n] = CurrentParticle[n];
          fitnesses[j] = CurrentFitness;
      }
      CrossoverParticle = null;
      NewParticle = null;
    }
    Pop_g = NewPopulation;
    NewPopulation = null;
    double oldfFParticle = fBest;
    double[] x_s = best;
    double posOld;
    double radius = 0.4 * (bounds[j][1] - bounds[j][0]);
    double fPart = problem.f(best);
    for (int k = 1; k < problemDimension; k++)
    {
      oldfFParticle = fBest;
      posOld = best[k];
      x_s[k] = best[k] - radius;
      x_s = toro(x_s, bounds);
      fBest = problem.f(x_s);
      i++;
      if (fBest < fPart)
      {
        best[j] = x_s[j];
      }
      else
      {
        x_s[j] = best[j];
        x_s[j] = best[j] + (radius/2);
        if (fBest < fPart)
        {
          best[j] = x_s[j];
        }
        else
        {
          x_s[j] = best[j];
        }
      }
      if (fPart == oldfFParticle)
      {
        radius = (radius/2);
      }
    }
    Pop_g = NewPopulation;
    NewPopulation = null;
}
finalBest = best;
FT.add(1, fBest);
return FT;
