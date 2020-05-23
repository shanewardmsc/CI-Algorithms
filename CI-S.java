double[] x_s = x_best;
double radius = 0.4 * (bounds[i][1] - bounds[i][0]);
while (i < maxEvaluations) // Condition on budget = No of Runs
{
  for (int j = 1; j < problemDimension; j++)
  {
    oldfFParticle = fParticle;
    posOld = x_best[j];
    x_s[j] = x_best[j] - radius;
    x_s = toro(x_s, bounds);//Used to saturate solution if outside bounds fParticle = problem.f(x_s);
    i++; // Increment counter

    if (fParticle <= problem.f(x_best))
    {
      x_best[j] = x_s[j];
    }
    else
    {
      x_s[j] = x_best[j];
      x_s[j] = x_best[j] + (radius/2);
      
      if (fParticle <= problem.f(x_best))
      {
        x_best[j] = x_s[j];
      }
      else
      {
        x_s[j] = x_best[j];
      }
    }
  }
  
  if (problem.f(x_best) == oldfFParticle)
  {
    radius = (radius/2);
  }

  FT.add(i, problem.f(x_best)); // Add to fitness trend
}
